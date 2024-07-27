package com.attractions;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.ApplicationContext;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class AttractionsServiceApplicationTests {
	@Autowired
	private ApplicationContext applicationContext;
	
	/**
     * Test to ensure the application context loads successfully.
     */
	@Test
	void contextLoads() {
		 assertThat(applicationContext).isNotNull();
	}
	
	/**
     * Test to ensure the main method runs without throwing exceptions.
     */
	@Test
    void testMain() {       
		assertDoesNotThrow(() ->  AttractionsServiceApplication.main(new String[]{}));       
    }
}
