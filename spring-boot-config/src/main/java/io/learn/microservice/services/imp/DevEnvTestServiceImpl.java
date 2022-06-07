package io.learn.microservice.services.imp;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import io.learn.microservice.services.EnvTestService;

@Service
@Profile("dev")
public class DevEnvTestServiceImpl implements EnvTestService {
	public void envDescription() {
		System.out.println("Dev");
	}
}
