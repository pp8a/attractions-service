package com.attractions.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attractions.dto.CityDTO;
import com.attractions.exception.EntityNotFoundException;
import com.attractions.service.CityService;
/**
 * REST controller for managing cities.
 */
@RestController
@RequestMapping("/cities")
public class CityController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);
	private CityService cityService;

	public CityController(CityService cityService) {
		super();
		this.cityService = cityService;
	}
	
	 /**
     * Creates a new city.
     *
     * @param cityDTO the city DTO
     * @return the created city
     */
	@PostMapping
	public ResponseEntity<CityDTO> createCity(
			@RequestBody CityDTO cityDTO){
		LOGGER.debug("Received request to create city: {}", cityDTO);
		CityDTO createdCity = cityService.createCity(cityDTO);
		LOGGER.info("City created with NAME: {} and ID: {}", 
				createdCity.getName(),
				createdCity.getId());
		return new ResponseEntity<>(createdCity, HttpStatus.CREATED);
	}
	
	 /**
     * Gets a city by ID.
     *
     * @param cityId the city ID
     * @return the city DTO
     */
	@GetMapping("/{cityId}")
	public ResponseEntity<CityDTO> getCityById(
			@PathVariable Long cityId){
		LOGGER.debug("Received request to fetch city with ID: {}", cityId);
		CityDTO cityDTO = cityService.getCityById(cityId)
				.orElseThrow(() -> {
					LOGGER.error("City not found with ID: {}", cityId);
					return new EntityNotFoundException("City not found with id: " + cityId);
				});
		LOGGER.info("Fetched city with ID: {}", cityId);						
		return ResponseEntity.ok(cityDTO);
	}
	
	/**
     * Gets all cities.
     *
     * @return the list of cities
     */
	@GetMapping
	public ResponseEntity<List<CityDTO>> getAllCities(){
		LOGGER.debug("Received request to fetch all cities");
		List<CityDTO> cities = cityService.getAllCities();
		LOGGER.info("Fetched all cities");
		return ResponseEntity.ok(cities);
	}

	/**
     * Updates the population and metro status of a city.
     *
     * @param cityId  the city ID
     * @param cityDTO the city DTO
     * @return the updated city DTO
     */
	@PutMapping("/{cityId}")
	public ResponseEntity<CityDTO> updateCityPopulationAndMetro(
	        @PathVariable Long cityId, 
	        @RequestBody CityDTO cityDTO){
		LOGGER.debug("Received request to update city with ID: {}", cityId);
		CityDTO updatedCity = cityService.updateCityPopulationAndMetro(cityId, cityDTO);
		LOGGER.info("City updated with ID: {}", updatedCity.getId());
		return ResponseEntity.ok(updatedCity);
	}
	
	/**
     * Deletes a city by ID.
     *
     * @param cityId the city ID
     * @return the response entity
     */
	@DeleteMapping("/{cityId}")
	public ResponseEntity<Void> deleteCity(
			@PathVariable Long cityId){
		LOGGER.debug("Received request to delete city with ID: {}", cityId);
		cityService.deleteCity(cityId);
		LOGGER.info("City deleted with ID: {}", cityId);
		return ResponseEntity.noContent().build();
		
	}
}