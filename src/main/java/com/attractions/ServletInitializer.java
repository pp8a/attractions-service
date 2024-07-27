package com.attractions;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer class to run the application in a servlet container.
 */
public class ServletInitializer extends SpringBootServletInitializer {
	/**
     * Configures the application by returning an instance of SpringApplicationBuilder.
     *
     * @param application SpringApplicationBuilder instance
     * @return configured SpringApplicationBuilder instance
     */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AttractionsServiceApplication.class);
	}

}
