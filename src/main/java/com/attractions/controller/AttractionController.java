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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.attractions.dto.AttractionDTO;
import com.attractions.exception.EntityNotFoundException;
import com.attractions.service.AttractionService;
/**
 * REST controller for managing attractions.
 */
@RestController
@RequestMapping("/attractions")
public class AttractionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AttractionController.class);
	private AttractionService attractionService;

	public AttractionController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}
	
	/**
	 * Creates a new attraction.
	 * 
	 * @param attractionDTO attractionDTO the attraction DTO
	 * @return the created attraction
	 */
	@PostMapping
	public ResponseEntity<AttractionDTO> createAttraction(
			@RequestBody AttractionDTO attractionDTO){
		LOGGER.debug("Received request to create attraction: {}", attractionDTO);
		AttractionDTO createdAttraction = attractionService
				.createAttraction(attractionDTO);
		LOGGER.info("City created with NAME: {} and ID: {}", 
				createdAttraction.getName(), 
				createdAttraction.getId());
		return new ResponseEntity<>(createdAttraction, HttpStatus.CREATED);		
	}
	
	/**
     * Gets an attraction by ID.
     *
     * @param attractionId the attraction ID
     * @return the attraction DTO
     */
	@GetMapping("/{attractionId}")
	public ResponseEntity<AttractionDTO> getAttractionById(
			@PathVariable Long attractionId){
		LOGGER.debug("Received request to fetch attraction with ID: {}", attractionId);
		AttractionDTO attractionDTO = attractionService
				.getAttractionById(attractionId)
				.orElseThrow(() -> {
					LOGGER.error("Attraction not found with ID: {}", attractionId);
					return new EntityNotFoundException("Attraction not found with id: " + attractionId);
				});
		LOGGER.info("Fetched attraction with ID: {}", attractionId);		
		return ResponseEntity.ok(attractionDTO);		
	}
	
	/**
     * Gets all attractions, with optional sorting by name and filtering by type.
     *
     * @param sortByName the sort order for the name (asc/desc)
     * @param type       the type of the attraction
     * @return the list of attractions
     */
	@GetMapping
	public ResponseEntity<List<AttractionDTO>> getAllAttractions(
			@RequestParam(required = false) String sortByName, 
			@RequestParam(required = false) String type) {
		LOGGER.debug("Received request to fetch all attractions");
		List<AttractionDTO> attractions;
		if(type != null) {
			LOGGER.debug("Filtering attractions by type: {}", type);
			attractions = attractionService.getAllAttractionsFilteredByType(type);
		} else if ("asc".equalsIgnoreCase(sortByName)){
			LOGGER.debug("Sorting attractions by name in ascending order");
			attractions = attractionService.getAllAttractionsSortedByName();
		} else {
			attractions = attractionService.getAllAttractions();
		}
		LOGGER.info("Fetched all attractions");
		return ResponseEntity.ok(attractions);
	}
	
	/**
     * Gets all attractions for a specific city by city ID.
     *
     * @param cityId the city ID
     * @return the list of attractions in the city
     */
	@GetMapping("/city/{cityId}")
    public ResponseEntity<List<AttractionDTO>> getAllAttractionsByCityId(
    		@PathVariable Long cityId) {
		LOGGER.debug("Received request to fetch all attractions for city with ID: {}", cityId);
        List<AttractionDTO> attractions = attractionService
        		.getAllAttractionsByCityId(cityId);
        LOGGER.info("Fetched all attractions for city with ID: {}", cityId);
        return ResponseEntity.ok(attractions);
    }
	
	/**
     * Updates the description of an attraction.
     *
     * @param attractionId the attraction ID
     * @param description  the new description
     * @return the updated attraction DTO
     */
	@PutMapping("/{attractionId}")
	public ResponseEntity<AttractionDTO> updateAttractionDescription(
            @PathVariable Long attractionId, 
            @RequestBody AttractionDTO description) {
		LOGGER.debug("Received request to update attraction with ID: {}", attractionId);
        AttractionDTO updatedAttraction = attractionService
        		.updateAttractionDescription(attractionId, description);
        LOGGER.info("Attraction updated with ID: {}", updatedAttraction.getId());
        return ResponseEntity.ok(updatedAttraction);
    }
	
	/**
     * Deletes an attraction by ID.
     *
     * @param attractionId the attraction ID
     * @return the response entity
     */
	@DeleteMapping("/{attractionId}")
    public ResponseEntity<Void> deleteAttraction(
    		@PathVariable Long attractionId) {
		LOGGER.debug("Received request to delete attraction with ID: {}", attractionId);
        attractionService.deleteAttraction(attractionId);
        LOGGER.info("Attraction deleted with ID: {}", attractionId);
        return ResponseEntity.noContent().build();
    }	
}