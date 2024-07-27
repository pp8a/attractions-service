package com.attractions.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
/**
 * Data Transfer Object (DTO) for Service.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDTO {
	/**
     * The unique identifier of the service.
     */
	private Long id;
	/**
     * The name of the service.
     */
    private String name;
    /**
     * A brief description of the service.
     */
    private String description;
    /**
     * The list of attractions associated with the service.
     */
    private List<AttractionDTO> attractionsDtos;
}
