package io.egen.app.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import io.egen.app.entity.Movie;
import io.egen.app.entity.Review;
import io.egen.app.exception.SearchException;

@Repository
public class MovieRepositoryImpl implements MovieRepository {
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Movie> findAll() {
		TypedQuery<Movie> query = em.createNamedQuery("Movie.findAll", Movie.class);
		return query.getResultList();
	}

	@Override
	public List<Movie> findAllPagination(int offset, int limit) {
		TypedQuery<Movie> query = em.createNamedQuery("Movie.findAll", Movie.class);
		List<Movie> movieList = query.getResultList();
		if (offset + limit > movieList.size())
			return new ArrayList<Movie>();
		return movieList;
	}

	@Override
	public Movie findOne(String movieId) {
		return em.find(Movie.class, movieId);
	}

	@Override
	public Movie create(Movie movie) {
		em.persist(movie);
		return movie;
	}

	@Override
	public Movie findByImdbID(String imdbID) {
		TypedQuery<Movie> query = em.createNamedQuery("Movie.findByImdbID", Movie.class);
		query.setParameter("pImdbId", imdbID);
		List<Movie> movies = query.getResultList();
		if (movies.size() == 1) {
			return movies.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Movie update(Movie movie) {
		return em.merge(movie);
	}

	@Override
	public void delete(Movie existing) {
		em.remove(existing);
	}

	@Override
	public Set<Review> findReviewsByMovieId(String movieId) {
		Movie movie = em.find(Movie.class, movieId);
		if (movie != null) {
			return movie.getReviews();
		}
		return null;
	}

	@Override
	public List<Movie> findMoviesByType(String type) {
		TypedQuery<Movie> query = em.createNamedQuery("Movie.findMoviesByType", Movie.class);
		query.setParameter("pType", type);
		return query.getResultList();
	}

	@Override
	public List<Movie> findMoviesByTypePagination(String type, int offset, int limit) {
		TypedQuery<Movie> query = em.createNamedQuery("Movie.findMoviesByType", Movie.class);
		query.setParameter("pType", type);
		List<Movie> movieList = query.getResultList();
		if (offset + limit > movieList.size())
			return new ArrayList<Movie>();
		return movieList;
	}

	@Override
	public List<Movie> findTopRatedMovies() {
		TypedQuery<Movie> query = em.createNamedQuery("Movie.findTopRatedMovies", Movie.class);
		return query.getResultList();
	}

	@Override
	public List<Movie> findTopRatedMoviesByType(String type) {
		TypedQuery<Movie> query = em.createNamedQuery("Movie.findTopRatedMoviesByType", Movie.class);
		query.setParameter("pType", type);
		return query.getResultList();
	}

	@Override
	public List<Movie> findSortedMovies(String sort) {
		if (sort != null && !sort.isEmpty()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<Movie> q = cb.createQuery(Movie.class);
			Root<Movie> c = q.from(Movie.class);
			q.select(c);
			if (sort.indexOf("-") == 0) {
				q.orderBy(cb.desc(c.get(sort.substring(1))));
			} else {
				q.orderBy(cb.asc(c.get(sort)));
			}

			return em.createQuery(q).getResultList();
		}
		return new ArrayList<Movie>();
	}

	@Override
	public List<Movie> findFilterMovies(String type, int year, String genre) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Movie> q = criteriaBuilder.createQuery(Movie.class);

		Root<Movie> rootObj = q.from(Movie.class);

		Predicate predicate = null;
		if (type != null && !type.isEmpty()) {
			predicate = criteriaBuilder.equal(rootObj.get("type"), type);
		}
		if (year > 0) {
			if (predicate != null) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(rootObj.get("year"), year));

			} else {
				predicate = criteriaBuilder.equal(rootObj.get("year"), year);
			}
		}

		if (genre != null && !genre.isEmpty()) {
			if (predicate != null) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.like(rootObj.get("genre"), "%" + genre + "%"));

			} else {
				predicate = criteriaBuilder.like(rootObj.get("genre"), "%" + genre + "%");
			}
		}
		if (predicate != null) {
			q.where(predicate);
		}
		return em.createQuery(q).getResultList();

	}

	@Override
	public List<Movie> searchMovies(String searchString, boolean isNumeric) {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
			fullTextEntityManager.createIndexer().startAndWait();
			QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Movie.class).get();

			@SuppressWarnings("rawtypes")
			BooleanJunction<BooleanJunction> booleanJunction = qb.bool();
			booleanJunction.should(qb.keyword().onFields("title", "rated", "genre", "director", "writer", "actors",
					"language", "country", "awards", "type").matching(searchString).createQuery());

			if (isNumeric) {
				Float number = Float.parseFloat(searchString);

				booleanJunction.should(
						qb.keyword().onFields("year").ignoreAnalyzer().matching(number.intValue()).createQuery());

				booleanJunction.should(qb.keyword().onFields("imdbRating").ignoreAnalyzer()
						.matching(number.floatValue()).createQuery());
			}

			org.apache.lucene.search.Query luceneQuery = booleanJunction.createQuery();

			javax.persistence.Query jpqQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Movie.class);

			@SuppressWarnings("unchecked")
			List<Movie> result = (List<Movie>) jpqQuery.getResultList();
			return result;
		} catch (InterruptedException e) {
			new SearchException(" Search can not be performed. ");
		}
		return new ArrayList<Movie>();
	}

}
