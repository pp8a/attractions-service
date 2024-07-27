package com.attractions.service.impl;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.attractions.data.TestData;
import com.attractions.dto.AttractionDTO;
import com.attractions.dto.CityDTO;
import com.attractions.exception.EntityDeletionException;
import com.attractions.exception.EntityNotFoundException;
import com.attractions.mapper.AttractionMapper;
import com.attractions.mapper.CityMapper;
import com.attractions.model.Attraction;
import com.attractions.model.City;
import com.attractions.repository.CityRepository;
import com.attractions.service.CityService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for CityService implementation.
 */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
class CityServiceImplTest {
	@MockBean
	private CityRepository cityRepository;	
	@MockBean
	private CityMapper cityMapper;
	@MockBean
	private AttractionMapper attractionMapper;
	
	@Autowired
	private CityService cityService;
	
	private City city;
	private CityDTO cityDTO;
	private Attraction attraction;
	private AttractionDTO attractionDTO;
	
	@BeforeEach
	void setUp(){
		city = TestData.createTestCity();
		cityDTO = TestData.createTestCityDTO();
		attraction = TestData.createTestAttraction();
        attractionDTO = TestData.createTestAttractionDTO();
		
		when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
		when(cityMapper.toDTO(city)).thenReturn(cityDTO);
		when(cityMapper.toEntity(cityDTO)).thenReturn(city);
		
		when(attractionMapper.toDTO(attraction)).thenReturn(attractionDTO);
	}
	
	@Test
    void testCreateCity() {
		when(cityRepository.save(any(City.class))).thenReturn(city);
		
		CityDTO createdCity = cityService.createCity(cityDTO);
		
		assertNotNull(createdCity);
		assertEquals(cityDTO.getId(), createdCity.getId());
		assertEquals(cityDTO.getAttractions().size(), createdCity.getAttractions().size());
		
		verify(cityRepository, times(1)).save(any(City.class));		
	}
	
	@Test
    void testGetCityById() {
        Optional<CityDTO> foundCity = cityService.getCityById(1L);

        assertTrue(foundCity.isPresent());
        assertEquals(cityDTO.getId(), foundCity.get().getId());
        assertEquals(cityDTO.getAttractions().size(), foundCity.get().getAttractions().size());
        
        verify(cityRepository, times(1)).findById(1L);
        verify(attractionMapper, times(city.getAttractions().size())).toDTO(any(Attraction.class));
    }
	
	@Test
    void testGetAllCities() {
        when(cityRepository.findAll()).thenReturn(Collections.singletonList(city));
        List<CityDTO> cities = cityService.getAllCities();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals(cityDTO.getAttractions().size(), cities.get(0).getAttractions().size());
        
        verify(cityRepository, times(1)).findAll();        
        verify(attractionMapper, times(city.getAttractions().size())).toDTO(any(Attraction.class));
    }
	
	@Test
    void testUpdateCityPopulationAndMetro() {
		cityDTO.setPopulation(100000L);
        cityDTO.setHasMetro(true);
        
        when(cityRepository.saveAndFlush(any(City.class))).thenReturn(city);
        CityDTO updatedCity = cityService.updateCityPopulationAndMetro(1L, cityDTO);
        
        assertNotNull(updatedCity);
        assertEquals(cityDTO.getPopulation(), updatedCity.getPopulation());
        assertEquals(cityDTO.getHasMetro(), updatedCity.getHasMetro());
        assertEquals(cityDTO.getAttractions().size(), updatedCity.getAttractions().size());
        verify(cityRepository, times(1)).findById(1L);
        verify(cityRepository, times(1)).saveAndFlush(any(City.class));        
	}
	
	@Test
    void testUpdateCityPopulationAndMetroNotFound() {
		Long cityId = 99L;
		
		cityDTO.setPopulation(100000L);
        cityDTO.setHasMetro(true);
        
        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, 
        		() -> cityService.updateCityPopulationAndMetro(cityId, cityDTO));
        
        verify(cityRepository, times(1)).findById(cityId);
        verify(cityRepository, times(0)).saveAndFlush(any(City.class));
	}
	
	@Test
    void testDeleteCity() {
        when(cityRepository.existsById(1L)).thenReturn(true);

        cityService.deleteCity(1L);

        verify(cityRepository, times(1)).existsById(1L);
        verify(cityRepository, times(1)).deleteById(1L);
    }
	
	@Test
    void testDeleteCityNotFound() {
        when(cityRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityDeletionException.class, 
        		() -> cityService.deleteCity(1L));

        verify(cityRepository, times(1)).existsById(1L);
        verify(cityRepository, times(0)).deleteById(1L);
    }
}
