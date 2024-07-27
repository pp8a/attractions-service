package com.attractions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.factory.Mappers;

import com.attractions.dto.AttractionDTO;
import com.attractions.model.Attraction;

/**
 * Mapper for the entity Attraction and its DTO AttractionDTO.
 */
@Mapper(componentModel = "spring", uses = {AttractionTypeMapper.class, ServiceMapper.class})
public interface AttractionMapper {
    AttractionMapper INSTANCE = Mappers.getMapper(AttractionMapper.class);
    
    /**
     * Converts an Attraction entity to an AttractionDTO.
     *
     * @param attraction the attraction entity
     * @return the attraction DTO
     */
    @Mapping(source = "type", target = "type", qualifiedByName = "toDto")  
    @Mapping(source = "city.id", target = "cityId")    
    @Mapping(source = "services", target = "servicesDtos")
    AttractionDTO toDTO(Attraction attraction);    
   
    /**
     * Converts an AttractionDTO to an Attraction entity.
     *
     * @param attractionDTO the attraction DTO
     * @return the attraction entity
     */
    @Mapping(source = "type", target = "type", qualifiedByName = "toEntity")
    @Mapping(source = "cityId", target = "city.id")   
    @Mapping(target = "services", ignore = true) 
    Attraction toEntity(AttractionDTO attractionDTO);
}