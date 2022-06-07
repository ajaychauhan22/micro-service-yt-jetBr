package io.learn.microservice.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.learn.microservice.models.Rating;
import io.learn.microservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {

	@GetMapping("/{movieId}")
	public Rating getRatings(@PathVariable("movieId") String movieId) {
		System.out.println(movieId);
		return new Rating("Transformer", 4);
	}

	@GetMapping("/users/{userId}")
	public UserRating getUserMovies(@PathVariable("userId") String userId) {
		System.out.println(userId);
		UserRating userRating = new UserRating();
		userRating.initData(userId);
		return userRating;
	}

}
