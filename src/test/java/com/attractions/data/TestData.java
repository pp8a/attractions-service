package com.attractions.data;

import java.util.Date;
import java.util.List;

import com.attractions.dto.AttractionDTO;
import com.attractions.dto.AttractionTypeDTO;
import com.attractions.dto.CityDTO;
import com.attractions.dto.ServiceDTO;
import com.attractions.model.Attraction;
import com.attractions.model.AttractionType;
import com.attractions.model.City;
import com.attractions.model.ServiceTour;

/**
 * Utility class for creating test data.
 */
public class TestData {
	
	private TestData() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
	
	/**
     * Creates a test city.
     *
     * @return the test city
     */
	public static City createTestCity() {
        City city = new City();
        city.setId(1L);
        city.setName("Test City");
        city.setPopulation(500000L);
        city.setHasMetro(false);
        
        Attraction attraction = createTestAttraction();
        
        city.setAttractions(List.of(attraction));        
        return city;
	}
	
	 /**
     * Creates a test attraction.
     *
     * @return the test attraction
     */
	public static Attraction createTestAttraction() {
		Attraction attraction = new Attraction();
		attraction.setId(1L);
        attraction.setName("Test Attraction");
        attraction.setCreatedDate(new Date());
        attraction.setDescription("This is a test description.");
        attraction.setType(AttractionType.PARK);        
        
        ServiceTour service = createTestService();
        
        attraction.setServices(List.of(service));
        return attraction;
	}
	
	/**
     * Creates a test service.
     *
     * @return the test service
     */
	public static ServiceTour createTestService() {
		ServiceTour service = new ServiceTour();
		service.setId(1L);
		service.setName("Test Service");
		service.setDescription("This is a test description");
		
		return service;
	}
	
	/**
     * Creates a test city DTO.
     *
     * @return the test city DTO
     */
	public static CityDTO createTestCityDTO() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1L);
        cityDTO.setName("Test City");
        cityDTO.setPopulation(500000L);
        cityDTO.setHasMetro(false);
        
        AttractionDTO attractionDTO = createTestAttractionDTO();
        
        cityDTO.setAttractions(List.of(attractionDTO));        
        return cityDTO;
	}
	
	/**
     * Creates a test attraction DTO.
     *
     * @return the test attraction DTO
     */
	public static AttractionDTO createTestAttractionDTO() {
		AttractionDTO attractionDTO = new AttractionDTO();
		attractionDTO.setId(1L);
        attractionDTO.setName("Test Attraction");
        attractionDTO.setCreatedDate(new Date());
        attractionDTO.setDescription("This is a test description.");
        attractionDTO.setType(new AttractionTypeDTO("PARK"));      
        
        ServiceDTO serviceDTO = createTestServiceDTO();
        
        attractionDTO.setServicesDtos(List.of(serviceDTO));
        return attractionDTO;
	}
	
	/**
     * Creates a test service DTO.
     *
     * @return the test service DTO
     */
	public static ServiceDTO createTestServiceDTO() {
		ServiceDTO serviceDTO = new ServiceDTO();
		serviceDTO.setId(1L);
		serviceDTO.setName("Test Service");
		serviceDTO.setDescription("This is a test description");
		
		return serviceDTO;
	}
}