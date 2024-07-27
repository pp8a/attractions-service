package com.attractions.controller.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import com.attractions.controller.base.BaseAttractionControllerTest;

/**
 * Integration test class for AttractionController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Import(TestcontainersConfiguration.class)
class AttractionControllerIntegrationTest extends BaseAttractionControllerTest{
		
}
