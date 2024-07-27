package com.attractions.service;

import java.util.List;
import java.util.Optional;

import com.attractions.dto.ServiceDTO;

/**
 * Service interface for managing services.
 */
public interface ServiceService {
	/**
     * Creates a new service.
     *
     * @param serviceDTO the service DTO
     * @return the created service DTO
     */
	ServiceDTO createService(ServiceDTO serviceDTO);
	/**
     * Gets a service by ID.
     *
     * @param serviceId the service ID
     * @return the service DTO
     */
    Optional<ServiceDTO> getServiceById(Long serviceId);
    /**
     * Gets all services.
     *
     * @return the list of service DTOs
     */
    List<ServiceDTO> getAllServices();
    /**
     * Deletes a service by ID.
     *
     * @param serviceId the service ID
     */
    void deleteService(Long serviceId);
}