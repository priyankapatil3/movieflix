package io.egen.app.service;

import java.util.List;

import io.egen.app.entity.Review;

public interface ReviewService {

	public List<Review> findAll();

	public Review findOne(String reviewId);

	public Review create(Review review, String movieId, String userId);

	public Review update(String reviewId, Review review);

	public void remove(String reviewId);

	public float findAverageRatingsByMovieId(String movieId);

}
