package com.attractions.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing an attraction.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "attraction")
public class Attraction {
	/**
     * The unique identifier of the attraction.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	 /**
     * The name of the attraction.
     */
	@Column(name = "name", nullable = false)
	private String name;
	/**
     * The date when the attraction was created.
     */
	@Column(name = "created_date")
    private Date createdDate;
	/**
     * A brief description of the attraction.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    /**
     * The type of the attraction.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AttractionType type;
    /**
     * The city where the attraction is located.
     */
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)    
    private City city;
    /**
     * The list of services associated with the attraction.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "attraction_service", 
    	joinColumns = @JoinColumn(name = "attraction_id"),
    	inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<ServiceTour> services;
}