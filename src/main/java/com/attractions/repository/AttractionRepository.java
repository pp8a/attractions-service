package com.attractions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attractions.model.Attraction;
import com.attractions.model.AttractionType;

/**
 * Repository interface for Attraction entities.
 */
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
	 /**
     * Finds a list of attractions by city ID.
     *
     * @param cityId the city ID
     * @return the list of attractions
     */
	public List<Attraction> findByCityId(Long cityId);
	/**
     * Finds all attractions ordered by name in ascending order.
     *
     * @return the list of attractions
     */
	public List<Attraction> findAllByOrderByNameAsc();
	/**
     * Finds a list of attractions by type.
     *
     * @param type the type of attraction
     * @return the list of attractions
     */
	public List<Attraction> findByType(AttractionType type);
}
