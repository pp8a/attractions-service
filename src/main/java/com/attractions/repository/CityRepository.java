package com.attractions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attractions.model.City;

/**
 * Repository interface for City entities.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
}
