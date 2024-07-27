package com.attractions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.attractions.dto.ServiceDTO;
import com.attractions.model.ServiceTour;

/**
 * Mapper for the entity ServiceTour and its DTO ServiceDTO.
 */
@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);
    
    /**
     * Converts a ServiceTour entity to a ServiceDTO.
     *
     * @param service the service entity
     * @return the service DTO
     */
    @Mapping(target = "attractionsDtos", ignore = true)
    ServiceDTO toDTO(ServiceTour service);
    
    /**
     * Converts a ServiceDTO to a ServiceTour entity.
     *
     * @param serviceDTO the service DTO
     * @return the service entity
     */
    @Mapping(target = "attractions", ignore = true)
    ServiceTour toEntity(ServiceDTO serviceDTO);
} 