package com.attractions.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a service.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "service")
public class ServiceTour {
	/**
     * The unique identifier of the service.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	 /**
     * The name of the service.
     */
	@Column(name = "name", nullable = false)
	private String name;
	/**
     * A brief description of the service tour.
     */
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	/**
     * The list of attractions associated with the service tour.
     */
	@ManyToMany(mappedBy = "services")
	private List<Attraction> attractions;
}