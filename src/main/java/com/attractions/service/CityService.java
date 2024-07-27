package com.attractions.service;

import java.util.List;
import java.util.Optional;

import com.attractions.dto.CityDTO;

/**
 * Service interface for managing cities.
 */
public interface CityService {
	/**
     * Creates a new city.
     *
     * @param cityDTO the city DTO
     * @return the created city DTO
     */
	CityDTO createCity(CityDTO cityDTO);
	/**
     * Gets a city by ID.
     *
     * @param cityId the city ID
     * @return the city DTO
     */
	Optional<CityDTO> getCityById(Long cityId);
	/**
     * Gets all cities.
     *
     * @return the list of city DTOs
     */
	List<CityDTO> getAllCities();
	/**
     * Updates the population and metro status of a city.
     *
     * @param cityId the city ID
     * @param cityDTO the city DTO
     * @return the updated city DTO
     */
	CityDTO updateCityPopulationAndMetro(Long cityId, CityDTO cityDTO);	
	/**
     * Deletes a city by ID.
     *
     * @param cityId the city ID
     */
	void deleteCity(Long cityId);
}