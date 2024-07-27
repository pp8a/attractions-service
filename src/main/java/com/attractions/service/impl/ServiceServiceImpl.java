package com.attractions.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.attractions.dto.ServiceDTO;
import com.attractions.exception.EntityDeletionException;
import com.attractions.mapper.ServiceMapper;
import com.attractions.model.ServiceTour;
import com.attractions.repository.ServiceRepository;
import com.attractions.service.ServiceService;

/**
 * Service implementation for managing services.
 */
@Service
public class ServiceServiceImpl implements ServiceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceServiceImpl.class);
	
	private ServiceRepository serviceRepository;
	private ServiceMapper serviceMapper;
	
	public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
		super();
		this.serviceRepository = serviceRepository;
		this.serviceMapper = serviceMapper;
	}

	@Override
	public ServiceDTO createService(ServiceDTO serviceDTO) {
		LOGGER.debug("Creating a new service: {}", serviceDTO);
		ServiceTour service = serviceMapper.toEntity(serviceDTO);
		service = serviceRepository.save(service);
		LOGGER.info("Service created with ID: {}", service.getId());
		return serviceMapper.toDTO(service);
	}

	@Override
	public Optional<ServiceDTO> getServiceById(Long serviceId) {
		LOGGER.debug("Fetching service with ID: {}", serviceId);
		return serviceRepository.findById(serviceId).map(serviceMapper::toDTO);
	}

	@Override
	public List<ServiceDTO> getAllServices() {
		LOGGER.debug("Fetching all services");
		return serviceRepository.findAll().stream()
				.map(serviceMapper::toDTO)
				.toList();
	}

	@Override
	public void deleteService(Long serviceId) {
		LOGGER.debug("Deleting service with ID: {}", serviceId);
		if(serviceRepository.existsById(serviceId)) {
			serviceRepository.deleteById(serviceId);
			LOGGER.info("Service deleted with ID: {}", serviceId);
		} else {
			LOGGER.error("Service not found with ID: {}", serviceId);
			throw new EntityDeletionException("Service not found with id: " + serviceId);
		}		
	}
}
