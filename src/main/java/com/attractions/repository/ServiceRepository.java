package com.attractions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attractions.model.ServiceTour;

/**
 * Repository interface for Service entities.
 */
@Repository
public interface ServiceRepository extends JpaRepository<ServiceTour, Long> {

}
