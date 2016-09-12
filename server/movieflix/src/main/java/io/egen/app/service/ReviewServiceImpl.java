package io.egen.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.egen.app.entity.Movie;
import io.egen.app.entity.Review;
import io.egen.app.entity.User;
import io.egen.app.exception.EntityAlreadyExistException;
import io.egen.app.exception.EntityNotFoundException;
import io.egen.app.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository repository;

	@Autowired
	private MovieService movieService;

	@Autowired
	private UserService userService;

	@Override
	public List<Review> findAll() {
		return repository.findAll();
	}

	@Override
	public Review findOne(String reviewId) {
		Review review = repository.findOne(reviewId);
		if (review == null) {
			throw new EntityNotFoundException("Review not found");
		}
		return review;
	}

	@Transactional
	@Override
	public Review create(Review review, String movieId, String userId) {
		Review existing = repository.findOne(movieId, userId);
		if (existing != null) {
			throw new EntityAlreadyExistException("Review already exists with this id");
		}

		Movie movie = movieService.findOne(movieId);
		User user = userService.findOne(userId);
		if (movie == null || user == null) {
			throw new EntityNotFoundException(" User/ Movie does not exists.");
		}

		if (movie != null && user != null) {
			review.setMovie(movie);
			movie.getReviews().add(review);

			review.setUser(user);

			movieService.update(movieId, movie);
		}
		return review;
	}

	@Transactional
	@Override
	public Review update(String reviewId, Review review) {
		Review existing = repository.findOne(reviewId);
		if (existing == null) {
			throw new EntityNotFoundException("Review not found");
		}
		return repository.update(review);
	}

	@Transactional
	@Override
	public void remove(String reviewId) {
		Review existing = repository.findOne(reviewId);
		if (existing == null) {
			throw new EntityNotFoundException("Review not found");
		}
		repository.delete(existing);
	}

	@Override
	public float findAverageRatingsByMovieId(String movieId) {
		return repository.findAverageRatingsByMovieId(movieId);
	}

}
