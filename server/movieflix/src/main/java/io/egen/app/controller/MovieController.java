package io.egen.app.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.egen.app.entity.Movie;
import io.egen.app.entity.Review;
import io.egen.app.service.MovieService;

@RestController
@RequestMapping(value = "movies", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MovieController {

	@Autowired
	private MovieService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<Movie> findAll(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "offset", required = false) String offset,
			@RequestParam(value = "limit", required = false) String limit) {

		if (type == null) {
			if (limit != null && offset != null) {
				return service.findAllPagination(new Integer(offset), new Integer(limit));
			} else {
				return service.findAll();
			}
		} else {
			if (offset != null && limit != null && (Integer.parseInt(limit) > 0)) {
				return service.findMoviesByTypePagination(type, new Integer(offset), new Integer(limit));
			} else {
				return service.findMoviesByType(type);
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public Movie findOne(@PathVariable("id") String movieId) {
		return service.findOne(movieId);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Movie create(@Valid @RequestBody Movie movie) {
		return service.create(movie);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Movie update(@PathVariable("id") String movieId, @Valid @RequestBody Movie movie) {
		return service.update(movieId, movie);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable("id") String movieId) {
		service.remove(movieId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}/reviews")
	public Set<Review> findReviewsByMovieId(@PathVariable("id") String movieId) {
		return service.findReviewsByMovieId(movieId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "topratedcatlog")
	public List<Movie> findTopRatedMovies(@RequestParam(value = "type", required = false) String type) {
		if (type == null) {
			return service.findTopRatedMovies();
		}
		return service.findTopRatedMoviesByType(type);
	}

	@RequestMapping(method = RequestMethod.GET, value = "sortedcatlog")
	public List<Movie> findSortedMovies(@RequestParam(value = "sort", required = true) String sort) {
		return service.findSortedMovies(sort);
	}

	@RequestMapping(method = RequestMethod.GET, value = "filtercatlog")
	public List<Movie> findFilterMovies(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "genre", required = false) String genre) {
		int yearInt = 0;
		if (year != null) {
			yearInt = year;
		}
		return service.findFilterMovies(type, yearInt, genre);
	}

	@RequestMapping(method = RequestMethod.GET, value = "search")
	public List<Movie> searchMovies(@RequestParam(value = "searchString", required = true) String searchString) {
		return service.searchMovies(searchString);
	}

}
