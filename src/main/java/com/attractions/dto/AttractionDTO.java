package com.attractions.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Attraction.
 */
@Getter
@Setter
public class AttractionDTO {
	/**
     * The unique identifier of the attraction.
     */
	private Long id;
	/**
     * The name of the attraction.
     */
    private String name;
    /**
     * The date when the attraction was created.
     */
    private Date createdDate;
    /**
     * A brief description of the attraction.
     */
    private String description;
    /**
     * The type of the attraction.
     */
    private AttractionTypeDTO type;
    /**
     * The unique identifier of the city where the attraction is located.
     */
    private Long cityId;   
    /**
     * The list of services associated with the attraction.
     */
    private List<ServiceDTO> servicesDtos;
}
