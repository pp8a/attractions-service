package com.attractions.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

import com.attractions.dto.CityDTO;
import com.attractions.model.City;

/**
 * Mapper for the entity City and its DTO CityDTO.
 */
@Mapper(componentModel = "spring", uses = {AttractionMapper.class})
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    /**
     * Converts a City entity to a CityDTO.
     *
     * @param city the city entity
     * @return the city DTO
     */
    CityDTO toDTO(City city);

    /**
     * Converts a CityDTO to a City entity.
     *
     * @param cityDTO the city DTO
     * @return the city entity
     */
    City toEntity(CityDTO cityDTO);
}