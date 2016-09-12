package io.egen.app.service;

import java.util.List;
import java.util.Set;

import io.egen.app.entity.Movie;
import io.egen.app.entity.Review;

public interface MovieService {

	public List<Movie> findAll();

	public List<Movie> findAllPagination(int offset, int limit);

	public Movie findOne(String movieId);

	public Movie create(Movie movie);

	public Movie update(String movieId, Movie movie);

	public void remove(String movieId);

	public Set<Review> findReviewsByMovieId(String movieId);

	public List<Movie> findMoviesByType(String type);

	public List<Movie> findMoviesByTypePagination(String type, int offset, int limit);

	public List<Movie> findTopRatedMovies();

	public List<Movie> findTopRatedMoviesByType(String type);

	public List<Movie> findSortedMovies(String sort);

	public List<Movie> findFilterMovies(String type, int year, String genre);

	public List<Movie> searchMovies(String searchString);

}
