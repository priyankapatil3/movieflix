package io.egen.app.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@NamedQueries({ @NamedQuery(name = "Review.findAll", query = "SELECT r from Review r "),
		@NamedQuery(name = "Review.findByID", query = "SELECT r from Review r where r.id=:pId"),
		@NamedQuery(name = "Review.findAverageRating", query = "SELECT avg(r.rating) from Review r where r.rating > 0 and r.movie.id = :pMovieId") })
public class Review implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7848092166889496890L;

	@Id
	private String id;

	@NotNull
	private float rating;
	private String comment;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVIEID", nullable = false)
	private Movie movie;

	@JsonIgnoreProperties({ "password" })
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USERID", nullable = false)
	private User user;

	public Review() {
		id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
