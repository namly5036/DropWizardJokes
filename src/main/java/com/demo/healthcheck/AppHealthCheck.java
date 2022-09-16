package com.demo.healthcheck;

import com.codahale.metrics.health.HealthCheck;

import javax.ws.rs.client.Client;

public class AppHealthCheck extends HealthCheck {
	private final Client client;

	public AppHealthCheck(Client client) {
		super();
		this.client = client;
	}

	@Override
	protected Result check() {
		return Result.healthy();
	}
}