package com.attractions;

import org.springframework.boot.SpringApplication;

/**
 * Entry point for testing the AttractionsServiceApplication with Testcontainers configuration.
 */
public class TestAttractionsServiceApplication {
	/**
     * Main method to start the test application.
     *
     * @param args command-line arguments
     */
	public static void main(String[] args) {
		SpringApplication.from(AttractionsServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
