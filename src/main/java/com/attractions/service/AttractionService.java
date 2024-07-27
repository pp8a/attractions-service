package com.attractions.service;

import java.util.List;
import java.util.Optional;

import com.attractions.dto.AttractionDTO;

/**
 * Service interface for managing attractions.
 */
public interface AttractionService {
	/**
     * Creates a new attraction.
     *
     * @param attractionDTO the attraction DTO
     * @return the created attraction DTO
     */
	AttractionDTO createAttraction(AttractionDTO attractionDTO);
	/**
     * Gets an attraction by ID.
     *
     * @param attractionId the attraction ID
     * @return the attraction DTO
     */
    Optional<AttractionDTO> getAttractionById(Long attractionId);
    /**
     * Gets all attractions.
     *
     * @return the list of attraction DTOs
     */
    List<AttractionDTO> getAllAttractions();    
    /**
     * Gets all attractions sorted by name.
     *
     * @return the list of sorted attraction DTOs
     */
    List<AttractionDTO> getAllAttractionsSortedByName();
    /**
     * Gets all attractions filtered by type.
     *
     * @param type the type of attraction
     * @return the list of filtered attraction DTOs
     */
    List<AttractionDTO> getAllAttractionsFilteredByType(String type);
    /**
     * Gets all attractions by city ID.
     *
     * @param cityId the city ID
     * @return the list of attraction DTOs
     */
    List<AttractionDTO> getAllAttractionsByCityId(Long cityId);
    /**
     * Updates the description of an attraction.
     *
     * @param attractionId the attraction ID
     * @param description the new description
     * @return the updated attraction DTO
     */
    AttractionDTO updateAttractionDescription(Long attractionId, AttractionDTO description);
    /**
     * Deletes an attraction by ID.
     *
     * @param attractionId the attraction ID
     */
    void deleteAttraction(Long attractionId);
}