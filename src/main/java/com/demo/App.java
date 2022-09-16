package com.demo;

import com.demo.controller.JokeController;
import com.demo.healthcheck.AppHealthCheck;
import com.demo.healthcheck.HealthCheckController;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.time.Duration;

public class App extends Application<Configuration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	@Override
	public void initialize(Bootstrap<Configuration> b) {
	}

	@Override
	public void run(Configuration c, Environment e)
	{
		LOGGER.info("Registering REST resources");
		RateLimiterConfig config = RateLimiterConfig.custom()
				.limitForPeriod(5)
				.limitRefreshPeriod(Duration.ofMinutes(1))
				.timeoutDuration(Duration.ofMillis(2000))
				.build();

		RateLimiterRegistry registry = RateLimiterRegistry.of(config);
		RateLimiter limiter = registry.rateLimiter("chucknorris");

		ClientBuilder cb = ClientBuilder.newBuilder();
		cb.property("com.ibm.ws.jaxrs.client.timeout", "600000");
		cb.property("com.ibm.ws.jaxrs.client.connection.timeout", "100000");
		cb.property("com.ibm.ws.jaxrs.client.receive.timeout", "600000");
		final Client client = cb.build();
		e.jersey().register(new JokeController(limiter, client));

		e.healthChecks().register("APIHealthCheck", new AppHealthCheck(client));
		e.jersey().register(new HealthCheckController(e.healthChecks()));
	}

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}
}