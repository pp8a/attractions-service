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
import com.attractions.dto.ServiceDTO;
import com.attractions.service.ServiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base test class for ServiceController.
 */
public abstract class BaseServiceControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ServiceService serviceService;	
	@Autowired
	private ObjectMapper objectMapper;
	
	private ServiceDTO serviceDTO;
	
	/**
     * Sets up the test data before each test.
     */
	@BeforeEach
	void setUp() {
		serviceDTO = TestData.createTestServiceDTO();
	}
	
	/**
     * Tests the creation of a service.
     */
	@Test
    void testCreateService() {
        when(serviceService.createService(any(ServiceDTO.class))).thenReturn(serviceDTO);
        
        try {
            mockMvc.perform(post("/services")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(serviceDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(serviceDTO.getId().intValue())))
                    .andExpect(jsonPath("$.name", is(serviceDTO.getName())))
                    .andExpect(jsonPath("$.description", is(serviceDTO.getDescription())));
        } catch (JsonProcessingException ex) {
            fail("JSON processing failed: " + ex.getMessage());
        } catch (Exception e) {
            fail("Request failed: " + e.getMessage());
        }
        
        verify(serviceService, times(1)).createService(any(ServiceDTO.class));
    }
	
	/**
     * Tests getting a service by ID.
     */
	@Test
    void testGetServiceById() {
        when(serviceService.getServiceById(anyLong())).thenReturn(Optional.of(serviceDTO));
        
        try {
            mockMvc.perform(get("/services/{serviceId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(serviceDTO.getId().intValue())))
                    .andExpect(jsonPath("$.name", is(serviceDTO.getName())))
                    .andExpect(jsonPath("$.description", is(serviceDTO.getDescription())));
        } catch (Exception e) {
            fail("Request failed: " + e.getMessage());
        }
        
        verify(serviceService, times(1)).getServiceById(anyLong());
    }
	
	/**
	 * Tests getting all services.
	 */
	@Test
    void testGetAllServices() {
        when(serviceService.getAllServices()).thenReturn(Arrays.asList(serviceDTO));
        
        try {
            mockMvc.perform(get("/services")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(serviceDTO.getId().intValue())))
                    .andExpect(jsonPath("$[0].name", is(serviceDTO.getName())))
                    .andExpect(jsonPath("$[0].description", is(serviceDTO.getDescription())));
        } catch (Exception e) {
            fail("Request failed: " + e.getMessage());
        }
        
        verify(serviceService, times(1)).getAllServices();
    }
	
	/**
     * Tests deleting a service by ID.
     */
	@Test
    void testDeleteService() {
        doNothing().when(serviceService).deleteService(anyLong());
        
        try {
            mockMvc.perform(delete("/services/{serviceId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            fail("Request failed: " + e.getMessage());
        }
        
        verify(serviceService, times(1)).deleteService(anyLong());
    }
}
