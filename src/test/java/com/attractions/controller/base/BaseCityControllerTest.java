package com.attractions.controller.base;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.attractions.data.TestData;
import com.attractions.dto.CityDTO;
import com.attractions.service.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base test class for CityController.
 */
public abstract class BaseCityControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CityService cityService;
	@Autowired
	private ObjectMapper objectMapper;
	
	private CityDTO cityDTO;
	
	/**
     * Sets up the test data before each test.
     */
	@BeforeEach
	void setUp(){
		cityDTO = TestData.createTestCityDTO();
	}
	
	/**
     * Tests the creation of a city.
     */
	@Test
	void testCreateCity() {
		when(cityService.createCity(any(CityDTO.class))).thenReturn(cityDTO);
		
		try {
			mockMvc.perform(post("/cities")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(cityDTO)))
						.andExpect(status().isCreated())
						.andExpect(jsonPath("$.id", is(cityDTO.getId().intValue())))
						.andExpect(jsonPath("$.name", is(cityDTO.getName())));
		} catch (JsonProcessingException ex) {
			fail("JSON processing failed: " + ex.getMessage());
		} catch (Exception e) {
			fail("Request failed: " + e.getMessage());
		}
				
		verify(cityService, times(1)).createCity(any(CityDTO.class));
	}
	
	/**
     * Tests getting a city by ID.
     */
	@Test
	void testGetCityById() {
		when(cityService.getCityById(anyLong())).thenReturn(Optional.of(cityDTO));
		
		try {
			mockMvc.perform(get("/cities/{cityId}", 1L)
			        .contentType(MediaType.APPLICATION_JSON))
			        .andExpect(status().isOk())
			        .andExpect(jsonPath("$.id", is(cityDTO.getId().intValue())))
			        .andExpect(jsonPath("$.name", is(cityDTO.getName())))
			        .andExpect(jsonPath("$.population", 
			        		is(cityDTO.getPopulation().intValue())))
			        .andExpect(jsonPath("$.hasMetro", is(cityDTO.getHasMetro())))
			        .andExpect(jsonPath("$.attractions[0].id", 
			        		is(cityDTO.getAttractions().get(0).getId().intValue())))
			        .andExpect(jsonPath("$.attractions[0].name", 
			        		is(cityDTO.getAttractions().get(0).getName())))
			        .andExpect(jsonPath("$.attractions[0].description", 
			        		is(cityDTO.getAttractions().get(0).getDescription())));
		} catch (Exception e) {
			fail("Request failed: " + e.getMessage());
		}
		
		verify(cityService, times(1)).getCityById(anyLong());    
	}
	
	/**
     * Tests getting all cities.
     */
	@Test
    void testGetAllCities() {
		when(cityService.getAllCities()).thenReturn(Arrays.asList(cityDTO));
		
		try {
			mockMvc.perform(get("/cities", 1L)
			        .contentType(MediaType.APPLICATION_JSON))
			        .andExpect(status().isOk())
			        .andExpect(jsonPath("$[0].id", is(cityDTO.getId().intValue())))
			        .andExpect(jsonPath("$[0].name", is(cityDTO.getName())))
			        .andExpect(jsonPath("$[0].population", 
			        		is(cityDTO.getPopulation().intValue())))
			        .andExpect(jsonPath("$[0].hasMetro", is(cityDTO.getHasMetro())))
			        .andExpect(jsonPath("$[0].attractions[0].id", 
			        		is(cityDTO.getAttractions().get(0).getId().intValue())))
			        .andExpect(jsonPath("$[0].attractions[0].name", 
			        		is(cityDTO.getAttractions().get(0).getName())))
			        .andExpect(jsonPath("$[0].attractions[0].description",
			        		is(cityDTO.getAttractions().get(0).getDescription())));
		} catch (Exception e) {
			fail("Request failed: " + e.getMessage());
		}
		
		verify(cityService, times(1)).getAllCities();		
	}
	
	/**
     * Tests updating the population and metro status of a city.
     */
	@Test
    void testUpdateCityPopulationAndMetro() throws Exception {
        when(cityService.updateCityPopulationAndMetro(anyLong(), 
        		any(CityDTO.class))).thenReturn(cityDTO);

        mockMvc.perform(put("/cities/{cityId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cityDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(cityDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(cityDTO.getName())));

        verify(cityService, times(1)).updateCityPopulationAndMetro(anyLong(), any(CityDTO.class));
    }

	/**
     * Tests deleting a city by ID.
     */
	@Test
    void testDeleteCity() throws Exception {
        doNothing().when(cityService).deleteCity(anyLong());

        mockMvc.perform(delete("/cities/{cityId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(cityService, times(1)).deleteCity(anyLong());
    }
}
