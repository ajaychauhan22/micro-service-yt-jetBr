package io.learn.microservice.resources;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.learn.microservice.config.DBSettings;
import io.learn.microservice.services.EnvTestService;

@RestController
@RequestMapping("/greeting")
public class GreetingResource {

	@Value("${my.greeting:}")
	private String greetingMessage;

	@Value("Static Greeting message")
	private String staticGreetingMessage;

	@Value("${my.list.value}")
	private List<String> listValue;

	@Value("#{${dbValues}}")
	private Map<String, String> dbValues;

	@Autowired
	private DBSettings dbSettings;

	@Autowired
	EnvTestService envTestService;

	// @HystrixCommand(fallbackMethod = "getFallbackCatalog")
	@GetMapping("/{userName}")
	public String greeting(@PathVariable("userName") String userName) {
		System.out.println(staticGreetingMessage);
		System.out.println(listValue);
		System.out.println(dbValues);
		System.out.println(dbSettings.getConnection());
		System.out.println(dbSettings.getHost());
		System.out.println(dbSettings.getPort());
		envTestService.envDescription();
		System.out.println("................");
		return greetingMessage + " " + userName;
	}

	public String getFallbackCatalog(@PathVariable("userName") String userName) {
		return "Hello " + userName;
	}
}
