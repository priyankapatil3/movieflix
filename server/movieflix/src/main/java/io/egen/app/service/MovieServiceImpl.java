package io.egen.app.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.egen.app.entity.Movie;
import io.egen.app.entity.Review;
import io.egen.app.exception.EntityAlreadyExistException;
import io.egen.app.exception.EntityNotFoundException;
import io.egen.app.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository repository;

	@Override
	public List<Movie> findAll() {
		return repository.findAll();
	}

	@Override
	public Movie findOne(String movieId) {
		Movie movie = repository.findOne(movieId);
		if (movie == null) {
			throw new EntityNotFoundException("Movie not found");
		}
		return movie;
	}

	@Transactional
	@Override
	public Movie create(Movie movie) {
		Movie existing = repository.findByImdbID(movie.getImdbID());
		if (existing != null) {
			throw new EntityAlreadyExistException("Movie already exists with this ImdbId");
		}
		return repository.create(movie);
	}

	@Transactional
	@Override
	public Movie update(String movieId, Movie movie) {
		Movie existing = repository.findOne(movieId);
		if (existing == null) {
			throw new EntityNotFoundException("Movie not found");
		}
		return repository.update(movie);
	}

	@Transactional
	@Override
	public void remove(String movieId) {
		Movie existing = repository.findOne(movieId);
		if (existing == null) {
			throw new EntityNotFoundException("Movie not found");
		}
		repository.delete(existing);
	}

	public Set<Review> findReviewsByMovieId(String movieId) {
		return repository.findReviewsByMovieId(movieId);

	}

	@Override
	public List<Movie> findMoviesByType(String type) {
		return repository.findMoviesByType(type);
	}

	@Override
	public List<Movie> findAllPagination(int offset, int limit) {
		return repository.findAllPagination(offset, limit);
	}

	@Override
	public List<Movie> findMoviesByTypePagination(String type, int offset, int limit) {
		return repository.findMoviesByTypePagination(type, offset, limit);
	}

	@Override
	public List<Movie> findTopRatedMoviesByType(String type) {
		return repository.findTopRatedMoviesByType(type);
	}

	@Override
	public List<Movie> findTopRatedMovies() {
		return repository.findTopRatedMovies();
	}

	@Override
	public List<Movie> findSortedMovies(String sort) {
		return repository.findSortedMovies(sort);
	}

	@Override
	public List<Movie> findFilterMovies(String type, int year, String genre) {
		return repository.findFilterMovies(type, year, genre);
	}

	@Transactional
	@Override
	public List<Movie> searchMovies(String searchString) {
		/*
		 * if(isNumeric(searchString)){ return
		 * repository.searchMovies(searchString); } return
		 * repository.searchMoviesByString(searchString);
		 */

		return repository.searchMovies(searchString, isNumeric(searchString));
	}

	public static boolean isNumeric(String searchString) {
		if (searchString != null) {
			return searchString.matches("^\\d+(\\.\\d+)?$");
		}
		return false;
	}
}
