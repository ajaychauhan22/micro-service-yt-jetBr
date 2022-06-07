package io.learn.microservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/environment-details")
public class EnvironmentDetailsResource {

	@Autowired
	Environment env;

	@GetMapping
	public String getEnv() {
		return env.toString();
	}
}
