package com.attractions.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.attractions.data.TestData;
import com.attractions.dto.ServiceDTO;
import com.attractions.exception.EntityDeletionException;
import com.attractions.mapper.ServiceMapper;
import com.attractions.model.ServiceTour;
import com.attractions.repository.ServiceRepository;
import com.attractions.service.ServiceService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for ServiceService implementation.
 */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
class ServiceServiceImplTest {
	@MockBean
	private ServiceRepository serviceRepository;
	@MockBean
	private ServiceMapper serviceMapper;
	
	@Autowired
	private ServiceService serviceService;
	
	private ServiceTour service;
	private ServiceDTO serviceDTO;
	
	@BeforeEach
	void setUp() {
		service = TestData.createTestService();
		serviceDTO = TestData.createTestServiceDTO();
		
		when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
		when(serviceMapper.toDTO(service)).thenReturn(serviceDTO);
		when(serviceMapper.toEntity(serviceDTO)).thenReturn(service);
	}
	
	@Test
	void testCreateService() {
		when(serviceRepository.save(any(ServiceTour.class))).thenReturn(service);
		
		ServiceDTO createService = serviceService.createService(serviceDTO);
		
		assertNotNull(createService);
		assertEquals(serviceDTO.getId(), createService.getId());
		
		verify(serviceRepository, times(1)).save(any(ServiceTour.class));
	}
	
	@Test
	void testGetServiceById() {
		Optional<ServiceDTO> foundService = serviceService.getServiceById(1L);
		
		assertTrue(foundService.isPresent());
		assertEquals(serviceDTO.getId(), foundService.get().getId());
		
		verify(serviceRepository, times(1)).findById(1L);
	}
	
	@Test
	void testGetAllServices() {
		when(serviceRepository.findAll()).thenReturn(Collections.singletonList(service));
		
		List<ServiceDTO> services = serviceService.getAllServices();
		
		assertNotNull(services);
		assertEquals(1, services.size());
		
		verify(serviceRepository, times(1)).findAll();
	}
	
	@Test
	void testDeleteService() {
		Long existId = 1L;
		Long notFoundId = 99L;
		
		when(serviceRepository.existsById(existId)).thenReturn(true);
		when(serviceRepository.existsById(notFoundId)).thenReturn(false);
		
		serviceService.deleteService(existId);		
		assertThrows(EntityDeletionException.class, () -> serviceService.deleteService(notFoundId));
		
		verify(serviceRepository, times(1)).existsById(existId);
		verify(serviceRepository, times(1)).deleteById(existId);
		
		verify(serviceRepository, times(1)).existsById(notFoundId);
		verify(serviceRepository, times(0)).deleteById(notFoundId);
	}
}
