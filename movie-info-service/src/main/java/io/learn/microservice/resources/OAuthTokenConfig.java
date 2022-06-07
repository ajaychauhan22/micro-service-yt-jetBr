package io.learn.microservice.resources;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("oauth-token")
@Configuration
public class OAuthTokenConfig {

	private RequestConfig requestConfig;

	public RequestConfig getRequestConfig() {
		return requestConfig;
	}

	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	public static class RequestConfig {

		private String client_id;
		private String client_secret;
		private String username;
		private String password;

		public String getClient_id() {
			return client_id;
		}

		public void setClient_id(String client_id) {
			this.client_id = client_id;
		}

		public String getClient_secret() {
			return client_secret;
		}

		public void setClient_secret(String client_secret) {
			this.client_secret = client_secret;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}
}
