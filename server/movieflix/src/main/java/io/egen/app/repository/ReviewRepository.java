package io.egen.app.repository;

import java.util.List;

import io.egen.app.entity.Review;

public interface ReviewRepository {

	public Review findOne(String movieId, String userId);

	public Review findOne(String review);

	public void delete(Review existing);

	public Review update(Review review);

	public List<Review> findAll();

	public Review create(Review review);

	public float findAverageRatingsByMovieId(String movieId);

}
