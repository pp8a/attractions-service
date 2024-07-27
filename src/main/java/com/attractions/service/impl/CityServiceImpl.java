package com.attractions.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.attractions.dto.CityDTO;
import com.attractions.exception.EntityDeletionException;
import com.attractions.exception.EntityNotFoundException;
import com.attractions.mapper.AttractionMapper;
import com.attractions.mapper.CityMapper;
import com.attractions.model.City;
import com.attractions.repository.CityRepository;
import com.attractions.service.CityService;

/**
 * Service implementation for managing cities.
 */
@Service
public class CityServiceImpl implements CityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);
	
	private CityRepository cityRepository;	
	private CityMapper cityMapper;
	private AttractionMapper attractionMapper;
	
	public CityServiceImpl(
			CityRepository cityRepository, 
			CityMapper cityMapper, 
			AttractionMapper attractionMapper) {
		super();
		this.cityRepository = cityRepository;
		this.cityMapper = cityMapper;
		this.attractionMapper = attractionMapper;
	}

	@Override
	public CityDTO createCity(CityDTO cityDTO) {
		LOGGER.debug("Creating a new city: {}", cityDTO);		
		City city = cityMapper.toEntity(cityDTO);
		city = cityRepository.save(city);		
		LOGGER.info("City created with ID: {}", city.getId());
		return cityMapper.toDTO(city);
	}


	@Override
    public Optional<CityDTO> getCityById(Long cityId) {
		LOGGER.debug("Fetching city with ID: {}", cityId);
        return cityRepository.findById(cityId)
                .map(city -> {
                    CityDTO cityDTO = cityMapper.toDTO(city);
                    cityDTO.setAttractions(city.getAttractions().stream()
                            .map(attractionMapper::toDTO)
                            .toList());
                    return cityDTO;
                });
    }

	@Override
    public List<CityDTO> getAllCities() {
		LOGGER.debug("Fetching all cities");
        return cityRepository.findAll().stream()
                .map(city -> {
                    CityDTO cityDTO = cityMapper.toDTO(city);
                    cityDTO.setAttractions(city.getAttractions().stream()
                            .map(attractionMapper::toDTO)
                            .toList());
                    return cityDTO;
                })
                .toList();
    }

	@Override
	public CityDTO updateCityPopulationAndMetro(Long cityId, CityDTO cityDTO) {
		LOGGER.debug("Updating city with ID: {}", cityId);
		return cityRepository.findById(cityId).map(city -> {
			city.setPopulation(cityDTO.getPopulation());
			city.setHasMetro(cityDTO.getHasMetro());
			City updatedCity = cityRepository.saveAndFlush(city);
			LOGGER.info("City updated with ID: {}", updatedCity.getId());
			return cityMapper.toDTO(updatedCity);
		}).orElseThrow(() -> new EntityNotFoundException("City not found with id: " + cityId));
	}

	@Override
	public void deleteCity(Long cityId) {
		LOGGER.debug("Deleting city with ID: {}", cityId);
		if(cityRepository.existsById(cityId)) {
			cityRepository.deleteById(cityId);
			LOGGER.info("City deleted with ID: {}", cityId);
		} else {
			LOGGER.error("City not found with ID: {}", cityId);
	        throw new EntityDeletionException("City not found with id: " + cityId);
	    }
	}
}
