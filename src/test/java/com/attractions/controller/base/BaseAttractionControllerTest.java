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

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.attractions.data.TestData;
import com.attractions.dto.AttractionDTO;
import com.attractions.service.AttractionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base test class for AttractionController.
 */
public abstract class BaseAttractionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AttractionService attractionService;
	@Autowired
	private ObjectMapper objectMapper;
	
	private AttractionDTO attractionDTO;
	
	/**
     * Sets up the test data before each test.
     */
	@BeforeEach
	void setUp() {
		attractionDTO = TestData.createTestAttractionDTO();
	}
	
	/**
     * Tests the creation of an attraction.
     */
	@Test
	void testCreateAttraction() {
		when(attractionService.createAttraction(any(AttractionDTO.class))).thenReturn(attractionDTO);
		
		try {
			mockMvc.perform(post("/attractions")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(attractionDTO)))
						.andExpect(status().isCreated())
						.andExpect(jsonPath("$.id", is(attractionDTO.getId().intValue())))
						.andExpect(jsonPath("$.name", is(attractionDTO.getName())));
		} catch (JsonProcessingException ex) {
			fail("JSON processing failed: " + ex.getMessage());
		} catch (Exception e) {
			fail("Request failed: " + e.getMessage());
		}
		
		verify(attractionService, times(1)).createAttraction(any(AttractionDTO.class));
	}
	
	/**
     * Tests getting an attraction by ID.
     */
	@Test
    void testGetAttractionById() {
        when(attractionService.getAttractionById(anyLong())).thenReturn(Optional.of(attractionDTO));

        try {            
            mockMvc.perform(get("/attractions/{attractionId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(attractionDTO.getId().intValue())))
                    .andExpect(jsonPath("$.name", is(attractionDTO.getName())))
                    .andExpect(jsonPath("$.createdDate", is(getDateFormat())))
                    .andExpect(jsonPath("$.description", is(attractionDTO.getDescription())))
                    .andExpect(jsonPath("$.type.typeName", 
                    		is(attractionDTO.getType().getTypeName())))
                    .andExpect(jsonPath("$.servicesDtos[0].id", 
                    		is(attractionDTO.getServicesDtos().get(0).getId().intValue())))
                    .andExpect(jsonPath("$.servicesDtos[0].name", 
                    		is(attractionDTO.getServicesDtos().get(0).getName())))
                    .andExpect(jsonPath("$.servicesDtos[0].description", 
                    		is(attractionDTO.getServicesDtos().get(0).getDescription())));
        } catch (Exception e) {
            fail("Request failed: " + e.getMessage());
        }

        verify(attractionService, times(1)).getAttractionById(anyLong());
    }

	private String getDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String expectedDate = sdf.format(attractionDTO.getCreatedDate());
		if (expectedDate.endsWith("Z")) {
		    expectedDate = expectedDate.replace("Z", "+00:00");
		}
		return expectedDate;
	}
	
	/**
     * Tests getting all attractions.
     */
	@Test
    void testGetAllAttraction() {
        when(attractionService.getAllAttractions())
        	.thenReturn(Collections.singletonList(attractionDTO));

        try {            
            mockMvc.perform(get("/attractions")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(attractionDTO.getId().intValue())))
                    .andExpect(jsonPath("$[0].name", is(attractionDTO.getName())))
                    .andExpect(jsonPath("$[0].createdDate", is(getDateFormat())))
                    .andExpect(jsonPath("$[0].description", is(attractionDTO.getDescription())))
                    .andExpect(jsonPath("$[0].type.typeName", 
                    		is(attractionDTO.getType().getTypeName())))
                    .andExpect(jsonPath("$[0].servicesDtos[0].id", 
                    		is(attractionDTO.getServicesDtos().get(0).getId().intValue())))
                    .andExpect(jsonPath("$[0].servicesDtos[0].name", 
                    		is(attractionDTO.getServicesDtos().get(0).getName())))
                    .andExpect(jsonPath("$[0].servicesDtos[0].description", 
                    		is(attractionDTO.getServicesDtos().get(0).getDescription())));
        } catch (Exception e) {
            fail("Request failed: " + e.getMessage());
        }

        verify(attractionService, times(1)).getAllAttractions();
    }
	
	/**
     * Tests getting all attractions by city ID.
     */
	@Test
    void testGetAllAttractionsByCityId() {
        when(attractionService.getAllAttractionsByCityId(anyLong()))
        	.thenReturn(Collections.singletonList(attractionDTO));

        try {
            mockMvc.perform(get("/attractions/city/{cityId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(attractionDTO.getId().intValue())))
                    .andExpect(jsonPath("$[0].name", is(attractionDTO.getName())));
        } catch (Exception e) {
            fail("Request failed: " + e.getMessage());
        }

        verify(attractionService, times(1)).getAllAttractionsByCityId(anyLong());
    }
	
	/**
     * Tests updating the description of an attraction.
     */
	@Test
    void testUpdateAttractionDescription() {
        when(attractionService.updateAttractionDescription(anyLong(), any(AttractionDTO.class)))
        	.thenReturn(attractionDTO);

        try {
            mockMvc.perform(put("/attractions/{attractionId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(attractionDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(attractionDTO.getId().intValue())))
                    .andExpect(jsonPath("$.name", is(attractionDTO.getName())));
        } catch (Exception e) {
            fail("Request failed: " + e.getMessage());
        }

        verify(attractionService, times(1)).updateAttractionDescription(anyLong(), 
        		any(AttractionDTO.class));
    }

	/**
     * Tests deleting an attraction by ID.
     */
    @Test
    void testDeleteAttraction() {
        doNothing().when(attractionService).deleteAttraction(anyLong());

        try {
            mockMvc.perform(delete("/attractions/{attractionId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            fail("Request failed: " + e.getMessage());
        }

        verify(attractionService, times(1)).deleteAttraction(anyLong());
    }
}
