package com.attractions.controller.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import com.attractions.controller.base.BaseServiceControllerTest;

/**
 * Integration test class for ServiceController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Import(TestcontainersConfiguration.class)
public class ServiceControllerIntegrationTest extends BaseServiceControllerTest {

}