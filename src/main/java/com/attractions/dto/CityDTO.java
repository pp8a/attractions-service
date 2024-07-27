package com.attractions.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
/**
 * Data Transfer Object (DTO) for City.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDTO {
	/**
     * The unique identifier of the city.
     */
	private Long id;
	/**
     * The name of the city.
     */
    private String name;
    /**
     * The population of the city.
     */
    private Long population;
    /**
     * Indicates whether the city has a metro system.
     */
    private Boolean hasMetro;
    /**
     * The list of attractions in the city.
     */
    private List<AttractionDTO> attractions;
}
