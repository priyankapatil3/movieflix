package io.egen.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.egen.app.entity.Review;
import io.egen.app.service.ReviewService;

@RestController
@RequestMapping(value = "reviews", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReviewController {
	@Autowired
	private ReviewService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<Review> findAll() {
		return service.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public Review findOne(@PathVariable("id") String reviewId) {
		return service.findOne(reviewId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "{movieid}/{userid}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Review create(@RequestBody Review review, @PathVariable("movieid") String movieId,
			@PathVariable("userid") String userId) {
		return service.create(review, movieId, userId);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Review update(@PathVariable("id") String reviewId, @RequestBody Review review) {
		return service.update(reviewId, review);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable("id") String reviewId) {
		service.remove(reviewId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}/averagerating")
	public float findAverageRatingsByMovieId(@PathVariable("id") String movieId) {
		return service.findAverageRatingsByMovieId(movieId);
	}

}
