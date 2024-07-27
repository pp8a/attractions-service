package com.attractions.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.attractions.dto.AttractionDTO;
import com.attractions.exception.EntityDeletionException;
import com.attractions.exception.EntityNotFoundException;
import com.attractions.mapper.AttractionMapper;
import com.attractions.model.Attraction;
import com.attractions.model.AttractionType;
import com.attractions.repository.AttractionRepository;
import com.attractions.service.AttractionService;

/**
 * Service implementation for managing attractions.
 */
@Service
public class AttractionServiceImpl implements AttractionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AttractionServiceImpl.class);
	private AttractionRepository attractionRepository;
	private AttractionMapper attractionMapper;

	public AttractionServiceImpl(AttractionRepository attractionRepository, AttractionMapper attractionMapper) {
		super();
		this.attractionRepository = attractionRepository;
		this.attractionMapper = attractionMapper;
	}

	@Override
	public AttractionDTO createAttraction(AttractionDTO attractionDTO) {
		LOGGER.debug("Creating a new attraction: {}", attractionDTO);
		Attraction attraction = attractionMapper.toEntity(attractionDTO);
		attraction = attractionRepository.save(attraction);
		LOGGER.info("Attraction created with ID: {}", attractionDTO.getId());
		return attractionMapper.toDTO(attraction);
	}

	@Override
	public Optional<AttractionDTO> getAttractionById(Long attractionId) {	
		LOGGER.debug("Fetching attraction with ID: {}", attractionId);
		return attractionRepository.findById(attractionId).map(attractionMapper::toDTO);
	}

	@Override
	public List<AttractionDTO> getAllAttractions() {
		LOGGER.debug("Fetching all attractions");
		return attractionRepository.findAll().stream()
				.map(attractionMapper::toDTO)
				.toList();
	}

	@Override
	public List<AttractionDTO> getAllAttractionsSortedByName() {
		LOGGER.debug("Sortering attractions by name");
		List<AttractionDTO> attractions = attractionRepository.findAllByOrderByNameAsc().stream()
			.map(attractionMapper::toDTO)
			.toList();
		LOGGER.info("Found {} attractions sorted by name", attractions.size());
		return attractions;
	}

	@Override
	public List<AttractionDTO> getAllAttractionsFilteredByType(String type) {	
		LOGGER.debug("Filtering attractions by type: {}", type);
		List<AttractionDTO> attractions = attractionRepository.findByType(AttractionType.valueOf(type.toUpperCase()))
			.stream()
			.map(attractionMapper::toDTO)
			.toList();
		LOGGER.info("Found {} attractions of type {}", attractions.size(), type);
		return attractions;
	}
	
	@Override
	public List<AttractionDTO> getAllAttractionsByCityId(Long cityId) {
		LOGGER.debug("Getting all attractions with ID: {}", cityId);
		List<AttractionDTO> attractions = attractionRepository.findByCityId(cityId).stream()
			.map(attractionMapper::toDTO)
			.toList();
		LOGGER.info("Found {} attractions with ID: {}", attractions.size(), cityId);
		return attractions;
	}

	@Override
	public AttractionDTO updateAttractionDescription(Long attractionId, AttractionDTO attractionDTO) {
		LOGGER.debug("Updating attraction with ID: {}", attractionId);
		return attractionRepository.findById(attractionId).map(attraction -> {
			attraction.setDescription(attractionDTO.getDescription());
			Attraction updateAttraction = attractionRepository.saveAndFlush(attraction);
			LOGGER.info("Attraction updated with ID: {}", attractionDTO.getCityId());
			return attractionMapper.toDTO(updateAttraction);
		}).orElseThrow(() -> new EntityNotFoundException("Attraction not found with id: " + attractionId));
	}

	@Override
	public void deleteAttraction(Long attractionId) {
		LOGGER.debug("Deleting attraction with ID: {}", attractionId);
		if(attractionRepository.existsById(attractionId)) {
			attractionRepository.deleteById(attractionId);
			LOGGER.info("Attraction deleted with ID: {}", attractionId);
		} else {
			LOGGER.error("Attraction not found with ID: {}", attractionId);
			throw new EntityDeletionException("Attraction not found with id: " + attractionId);
		}
	}

}
