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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attractions.dto.ServiceDTO;
import com.attractions.exception.EntityNotFoundException;
import com.attractions.service.ServiceService;
/**
 * REST controller for managing services.
 */
@RestController
@RequestMapping("/services")
public class ServiceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);
	private ServiceService serviceService;
	
	public ServiceController(ServiceService serviceService) {
		super();
		this.serviceService = serviceService;
	}

	/**
     * Creates a new service.
     *
     * @param serviceDTO the service DTO
     * @return the created service
     */
	@PostMapping
	public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO){
		LOGGER.debug("Received request to create service: {}", serviceDTO);
		ServiceDTO createdService = serviceService
				.createService(serviceDTO);
		LOGGER.info("Service created with NAME: {} and ID: {}",
				createdService.getName(),
				createdService.getId());
		return new ResponseEntity<>(createdService, HttpStatus.CREATED);
	}
	
	/**
     * Gets a service by ID.
     *
     * @param serviceId the service ID
     * @return the service DTO
     */
	@GetMapping("/{serviceId}")
	public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Long serviceId){
		LOGGER.debug("Received request to fetch service with ID: {}", serviceId);
		ServiceDTO services = serviceService.getServiceById(serviceId)
				.orElseThrow(() -> {
					LOGGER.error("Service not found with ID: {}", serviceId);
					return new EntityNotFoundException("Service not found with id: " + serviceId);
				});
		LOGGER.info("Fetched service with ID: {}", serviceId);		
		return ResponseEntity.ok(services);
	}	
	
	/**
     * Gets all services.
     *
     * @return the list of services
     */
	@GetMapping
	public ResponseEntity <List<ServiceDTO>> getAllServices(){
		LOGGER.debug("Received request to fetch all services");
		List<ServiceDTO> services = serviceService.getAllServices();
		LOGGER.info("Fetched all services");
		return ResponseEntity.ok(services);
	}
	
	/**
     * Deletes a service by ID.
     *
     * @param serviceId the service ID
     * @return the response entity
     */
	@DeleteMapping("/{serviceId}")
	public ResponseEntity<Void> deleteService(@PathVariable Long serviceId){
		LOGGER.debug("Received request to delete service with ID: {}", serviceId);
		serviceService.deleteService(serviceId);
		LOGGER.info("Service deleted with ID: {}", serviceId);
		return ResponseEntity.noContent().build();
	}
}