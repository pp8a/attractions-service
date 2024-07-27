package com.attractions.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a city.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "city")
public class City {
	/**
     * The unique identifier of the city.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	/**
     * The name of the city.
     */
	@Column(name = "name", nullable = false)	
	private String name;
	/**
     * The population of the city.
     */
	@Column(name = "population")
    private Long population;
	/**
     * Indicates whether the city has a metro system.
     */
	@Column(name = "has_metro")
    private Boolean hasMetro;
	/**
     * The list of attractions in the city.
     */
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Attraction> attractions;
}