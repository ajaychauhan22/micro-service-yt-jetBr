package io.learn.microservice.resources;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.learn.microservice.models.Movie;
import io.learn.microservice.models.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieResource {

	@Value("${api.key}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/{movieId}")
	public Movie getMovieInfoDB(@PathVariable("movieId") String movieId) {
		System.out.println(movieId);
		
		MovieSummary movieSummary = restTemplate.getForObject(
				"https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey, MovieSummary.class);

		if (Objects.isNull(movieSummary)) {
			return new Movie();
		}
		return new Movie(movieSummary.getId(), movieSummary.getTitle(), movieSummary.getOverview());
	}

	@GetMapping("/v0/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
		System.out.println(movieId);
		return new Movie("Transformer", "Transformer Name", "Transformer Desc");
	}
}
