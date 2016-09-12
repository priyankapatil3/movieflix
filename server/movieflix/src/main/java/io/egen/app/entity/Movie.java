package io.egen.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
@NamedQueries({ @NamedQuery(name = "Movie.findAll", query = "SELECT m from Movie m "),
		@NamedQuery(name = "Movie.findByImdbID", query = "SELECT m from Movie m where m.imdbID=:pImdbId"),
		@NamedQuery(name = "Movie.findMoviesByType", query = "SELECT m from Movie m where m.type = :pType"),
		@NamedQuery(name = "Movie.findTopRatedMoviesByType", query = "SELECT m from Movie m where m.imdbRating >= 7 and m.type = :pType order by m.imdbRating desc"),
		@NamedQuery(name = "Movie.findTopRatedMovies", query = "SELECT m from Movie m where m.imdbRating >= 7 order by m.imdbRating desc"),
		@NamedQuery(name = "Movie.findMoviesByTypeYearGenre", query = "SELECT m from Movie m where m.type >= :pType and m.year = :pYear and m.genre LIKE :pGenre")

})
@Indexed
public class Movie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6246873469315723538L;

	@Id
	private String id;

	@NotNull
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String title;

	@NotNull
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@NumericField
	private int year;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String rated;

	@Temporal(TemporalType.DATE)
	private Date released;

	private String runtime;

	@NotNull
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String genre;

	@NotNull
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String director;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String writer;

	@NotNull
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String actors;

	private String plot;

	@NotNull
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String language;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String country;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String awards;
	private String poster;

	private int metascore;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private float imdbRating;

	private long imdbVotes;

	@Column(unique = true)
	private String imdbID;

	@NotNull
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String type;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "movie", cascade = CascadeType.ALL)
	private Set<Review> reviews = new HashSet<Review>();

	public Movie() {
		id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public int getMetascore() {
		return metascore;
	}

	public void setMetascore(int metascore) {
		this.metascore = metascore;
	}

	public float getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(float imdbRating) {
		this.imdbRating = imdbRating;
	}

	public long getImdbVotes() {
		return imdbVotes;
	}

	public void setImdbVotes(long imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", year=" + year + ", rated=" + rated + ", released=" + released
				+ ", runtime=" + runtime + ", genre=" + genre + ", director=" + director + ", writer=" + writer
				+ ", actors=" + actors + ", plot=" + plot + ", language=" + language + ", country=" + country
				+ ", awards=" + awards + ", poster=" + poster + ", metascore=" + metascore + ", imdbRating="
				+ imdbRating + ", imdbVotes=" + imdbVotes + ", imdbID=" + imdbID + ", type=" + type + "]";
	}

}
