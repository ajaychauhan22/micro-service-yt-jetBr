package io.learn.microservice.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.learn.microservice.models.CatalogItem;
import io.learn.microservice.models.Movie;
import io.learn.microservice.models.Rating;

@Service
public class MovieInfoService {

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") }, threadPoolKey = "movieInfoPool", threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "20"),
					@HystrixProperty(name = "maxQueueSize", value = "11") })
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),
				Movie.class);
		if (Objects.nonNull(movie)) {
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getMovieRating());
		}
		return new CatalogItem("No Movie", "No Desc", -1);
	}

	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie not found", "", -1);
	}

	/**
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") })
	public CatalogItem getCatalogItemHystrexParams(Rating rating) {
		Movie movie = restTemplateWithTimeOut.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),
				Movie.class);
		if (Objects.nonNull(movie)) {
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getMovieRating());
		}
		return new CatalogItem("No Movie", "No Desc", -1);
	}

	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000") }, threadPoolKey = "movieInfoPool", threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "20"),
					@HystrixProperty(name = "maxQueueSize", value = "10") })
	public CatalogItem getCatalogItemHystrexParamsAndBulheadHystrix(Rating rating) {
		Movie movie = restTemplateWithTimeOut.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),
				Movie.class);
		if (Objects.nonNull(movie)) {
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getMovieRating());
		}
		return new CatalogItem("No Movie", "No Desc", -1);
	}
	
	*/
}