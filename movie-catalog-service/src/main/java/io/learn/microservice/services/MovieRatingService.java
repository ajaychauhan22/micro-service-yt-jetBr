package io.learn.microservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.learn.microservice.models.Rating;
import io.learn.microservice.models.UserRating;

@Service
public class MovieRatingService {

	@Autowired
	@Qualifier("restTemplateWithTimeOut")
	private RestTemplate restTemplateWithTimeOut;

	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	public UserRating getUserRating(String userId) {
		return restTemplateWithTimeOut.getForObject("http://movie-rating-data-service/ratingsdata/users/" + userId,
				UserRating.class);
	}

	public UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}
}
