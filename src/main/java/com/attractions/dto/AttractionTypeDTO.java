package com.attractions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Data Transfer Object (DTO) for Attraction Type.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttractionTypeDTO {
	/**
     * The name of the attraction type.
     */
	private String typeName;	
}
