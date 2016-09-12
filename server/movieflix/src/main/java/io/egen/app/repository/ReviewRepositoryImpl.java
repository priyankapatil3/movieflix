package io.egen.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import io.egen.app.entity.Review;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

	@PersistenceContext
	private EntityManager em;

	public Review findOne(String reviewId) {
		return em.find(Review.class, reviewId);

	}

	@Override
	public Review findOne(String movieId, String userId) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Review> q = criteriaBuilder.createQuery(Review.class);

		Root<Review> rootObj = q.from(Review.class);
		Predicate predicate = criteriaBuilder.equal(rootObj.get("movie").<String> get("id"), movieId);
		predicate = criteriaBuilder.and(predicate,
				criteriaBuilder.equal(rootObj.get("user").<String> get("id"), userId));
		q.where(predicate);
		List<Review> reviews = em.createQuery(q).getResultList();
		if (reviews != null && reviews.size() > 0) {
			return reviews.get(0);
		}
		return null;
	}

	@Override
	public void delete(Review existing) {
		em.remove(existing);

	}

	@Override
	public Review update(Review review) {
		return em.merge(review);
	}

	@Override
	public List<Review> findAll() {
		TypedQuery<Review> query = em.createNamedQuery("Review.findAll", Review.class);
		return query.getResultList();
	}

	@Override
	public Review create(Review review) {
		em.persist(review);
		return review;
	}

	@Override
	public float findAverageRatingsByMovieId(String movieId) {
		TypedQuery<Double> query = em.createNamedQuery("Review.findAverageRating", Double.class);
		query.setParameter("pMovieId", movieId);
		Double result = query.getSingleResult();
		if (result != null)
			return result.floatValue();

		return 0.0f;
	}

}
