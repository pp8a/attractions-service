package com.attractions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.attractions.dto.AttractionTypeDTO;
import com.attractions.model.AttractionType;

/**
 * Mapper for the entity AttractionType and its DTO AttractionTypeDTO.
 */
@Mapper(componentModel = "spring")
public interface AttractionTypeMapper {
    AttractionTypeMapper INSTANCE = Mappers.getMapper(AttractionTypeMapper.class);

    /**
     * Converts an AttractionType entity to an AttractionTypeDTO.
     *
     * @param attractionType the attraction type entity
     * @return the attraction type DTO
     */
    @Named("toDto")
    default AttractionTypeDTO toDto(AttractionType attractionType) {
        if (attractionType == null) {
            return null;
        }

        AttractionTypeDTO dto = new AttractionTypeDTO();
        dto.setTypeName(attractionType.name());

        return dto;
    }

    /**
     * Converts an AttractionTypeDTO to an AttractionType entity.
     *
     * @param attractionTypeDTO the attraction type DTO
     * @return the attraction type entity
     */
    @Named("toEntity")
    default AttractionType toEntity(AttractionTypeDTO attractionTypeDTO) {
        if (attractionTypeDTO == null) {
            return null;
        }

        return AttractionType.valueOf(attractionTypeDTO.getTypeName());
    }
}
