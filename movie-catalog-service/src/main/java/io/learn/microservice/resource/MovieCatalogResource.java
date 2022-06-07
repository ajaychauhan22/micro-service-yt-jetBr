package io.learn.microservice.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.learn.microservice.models.CatalogItem;
import io.learn.microservice.models.Movie;
import io.learn.microservice.models.Rating;
import io.learn.microservice.models.UserRating;
import io.learn.microservice.services.MovieInfoService;
import io.learn.microservice.services.MovieRatingService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("restTemplateWithTimeOut")
	private RestTemplate restTemplateWithTimeOut;
	@Autowired
	MovieRatingService movieRatingService;

	@Autowired
	MovieInfoService movieInfoService;

	// @HystrixCommand(fallbackMethod = "getFallbackCatalog")
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		System.out.println(userId);
		List<CatalogItem> catalogs = new ArrayList<>();

		UserRating ratings = movieRatingService.getUserRating(userId);

		if (Objects.nonNull(ratings) && Objects.nonNull(ratings.getRatings())) {
			catalogs = ratings.getRatings().stream().map(movieInfoService::getCatalogItem).collect(Collectors.toList());
		}

		System.out.println(catalogs);
		return catalogs;
	}

	///////////////////////////////////////
	@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	@GetMapping("/v2/{userId}")
	public List<CatalogItem> getCatalogV2(@PathVariable("userId") String userId) {
		System.out.println(userId);
		List<CatalogItem> catalogs = new ArrayList<>();

		UserRating ratings = restTemplateWithTimeOut
				.getForObject("http://movie-rating-data-service/ratingsdata/users/" + userId, UserRating.class);

		if (Objects.nonNull(ratings) && Objects.nonNull(ratings.getRatings())) {
			catalogs = ratings.getRatings().stream().map(rating -> {
				Movie movie = restTemplateWithTimeOut
						.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
				return new CatalogItem(movie.getName(), "Action movie", rating.getMovieRating());
			}).collect(Collectors.toList());
		}

		System.out.println(catalogs);
		return catalogs;
	}

	public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
		System.out.println("getFallbackCatalog" + userId);
		return Arrays.asList(new CatalogItem("No Movie", "", 0));
	}

	////////////////////////////////
	@GetMapping("/discovery-client/{service-name}")
	public Object getDiscoveryClient(@PathVariable("service-name") String serviceName) {
		System.out.println(serviceName);
		// discoveryClient.
		// TODO
		return null;
	}

	@GetMapping("/v1/{userId}")
	public List<CatalogItem> getCatalogV1(@PathVariable("userId") String userId) {
		System.out.println(userId);

		List<Rating> ratings = Arrays.asList(new Rating("Transformer", 6), new Rating("Die Hard", 7));
		List<CatalogItem> catalogs = ratings.stream().map(rating -> {
			Movie movie = webClientBuilder.build().get().uri("http://localhost:9080/movies/" + rating.getMovieId())
					.retrieve().bodyToMono(Movie.class).block();
			return new CatalogItem(movie.getName(), "Action movie", rating.getMovieRating());
		}).collect(Collectors.toList());

		System.out.println(catalogs);
		return catalogs;
	}
}
