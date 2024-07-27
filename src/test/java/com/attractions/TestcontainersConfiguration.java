package com.attractions;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import org.testcontainers.junit.jupiter.Container;

/**
 * Test configuration class for setting up Testcontainers with a PostgreSQL container.
 */
@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {
	/**
     * PostgreSQL container for integration testing.
     */
	@Container
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
}