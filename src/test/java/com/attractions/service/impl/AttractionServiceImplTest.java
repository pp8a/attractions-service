package com.attractions.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.attractions.data.TestData;
import com.attractions.dto.AttractionDTO;
import com.attractions.exception.EntityDeletionException;
import com.attractions.exception.EntityNotFoundException;
import com.attractions.mapper.AttractionMapper;
import com.attractions.model.Attraction;
import com.attractions.model.AttractionType;
import com.attractions.repository.AttractionRepository;
import com.attractions.service.AttractionService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for AttractionService implementation.
 */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
class AttractionServiceImplTest {
	@MockBean
	private AttractionRepository attractionRepository;
	@MockBean
	private AttractionMapper attractionMapper;
	
	@Autowired
	private AttractionService attractionService;
	
	private Attraction attraction;
	private AttractionDTO attractionDTO;
	
	
	@BeforeEach
	void setUp(){
		attraction = TestData.createTestAttraction();
		attractionDTO = TestData.createTestAttractionDTO();
		
		when(attractionRepository.findById(1L)).thenReturn(Optional.of(attraction));
		when(attractionMapper.toDTO(attraction)).thenReturn(attractionDTO);
		when(attractionMapper.toEntity(attractionDTO)).thenReturn(attraction);
	}
	
	@Test
	void testCreateAttraction() {
		when(attractionRepository.save(any(Attraction.class))).thenReturn(attraction);
		
		AttractionDTO createAttraction = attractionService.createAttraction(attractionDTO);
		
		assertNotNull(createAttraction);
		assertEquals(attractionDTO.getId(), createAttraction.getId());
		assertEquals(attractionDTO.getServicesDtos().size(), 
				createAttraction.getServicesDtos().size());
		
		verify(attractionRepository, times(1)).save(any(Attraction.class));
	}
	
	@Test
	void testGetAttractionById() {
		Optional<AttractionDTO> foundAttraction = attractionService.getAttractionById(1L);
		
		assertTrue(foundAttraction.isPresent());
		assertEquals(attractionDTO.getId(), foundAttraction.get().getId());
		assertEquals(attractionDTO.getServicesDtos().size(), 
				foundAttraction.get().getServicesDtos().size());
		
		verify(attractionRepository, times(1)).findById(1L);
	}
	
	@Test
	void testAllAttractions() {
		when(attractionRepository.findAll()).thenReturn(Collections.singletonList(attraction));
		
		List<AttractionDTO> attractions = attractionService.getAllAttractions();
		
		assertNotNull(attractions);
		assertEquals(1, attractions.size());
		assertEquals(attractionDTO.getServicesDtos().size(), 
				attractions.get(0).getServicesDtos().size());
		
		verify(attractionRepository, times(1)).findAll();
	}
	
	@Test
	void testGetAllAttractionsSortedByName() {
		when(attractionRepository.findAllByOrderByNameAsc())
			.thenReturn(Collections.singletonList(attraction));
		
		List<AttractionDTO> sortedAttractions = attractionService.getAllAttractionsSortedByName();
		
		assertNotNull(sortedAttractions);
		assertEquals(1, sortedAttractions.size());
		assertEquals(attractionDTO.getServicesDtos().size(), 
				sortedAttractions.get(0).getServicesDtos().size());
		
		verify(attractionRepository, times(1)).findAllByOrderByNameAsc();
		verify(attractionMapper, times(1)).toDTO(any(Attraction.class));
	}
	
	@Test
	void testGetAllAttractionsFilteredByType() {
		String type = "PARK";
		when(attractionRepository.findByType(AttractionType.valueOf(type)))
			.thenReturn(Collections.singletonList(attraction));
		
		List<AttractionDTO> filteredAttractions = attractionService.getAllAttractionsFilteredByType(type);
		
		assertNotNull(filteredAttractions);
		assertEquals(1, filteredAttractions.size());
		assertEquals(attractionDTO.getServicesDtos().size(), 
				filteredAttractions.get(0).getServicesDtos().size());
		
		verify(attractionRepository, times(1)).findByType(AttractionType.valueOf(type));
		verify(attractionMapper, times(1)).toDTO(any(Attraction.class));
	}
	
	@Test
    void testGetAllAttractionsByCityId() {
        Long cityId = 1L;
        when(attractionRepository.findByCityId(cityId)).thenReturn(Collections.singletonList(attraction));
        List<AttractionDTO> attractionsByCityId = attractionService.getAllAttractionsByCityId(cityId);
        
        assertNotNull(attractionsByCityId);
        assertEquals(1, attractionsByCityId.size());
        assertEquals(attractionDTO.getCityId(), attractionsByCityId.get(0).getCityId());
        
        verify(attractionRepository, times(1)).findByCityId(cityId);
        verify(attractionMapper, times(1)).toDTO(any(Attraction.class));
    }
	
	@Test
	void testUpdateAttractionDescription() {
		Long attractionId = 1L;
		attractionDTO.setDescription("Test description");
		
		when(attractionRepository.saveAndFlush(any(Attraction.class))).thenReturn(attraction);
		
		AttractionDTO updateAttraction = attractionService.updateAttractionDescription(attractionId, attractionDTO);
		
		assertNotNull(updateAttraction);
		assertEquals(attractionDTO.getDescription(), updateAttraction.getDescription());
		
		verify(attractionRepository, times(1)).findById(attractionId);
		verify(attractionRepository, times(1)).saveAndFlush(any(Attraction.class));
	}
	
	@Test
	void testUpdateAttractionDescriptionNotFound() {
		Long attractionId = 99L;
		attractionDTO.setDescription("Test description");
		
		when(attractionRepository.findById(attractionId)).thenReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, 
				() -> attractionService.updateAttractionDescription(attractionId, attractionDTO));		
		
		verify(attractionRepository, times(1)).findById(attractionId);
		verify(attractionRepository, times(0)).saveAndFlush(any(Attraction.class));
	}
	
	@Test
	void testDeleteAttraction() {
		Long attractionId = 1L;
		when(attractionRepository.existsById(attractionId)).thenReturn(true);
		
		attractionService.deleteAttraction(attractionId);
		
		verify(attractionRepository, times(1)).existsById(attractionId);
		verify(attractionRepository, times(1)).deleteById(attractionId);
	}
	
	@Test
	void testDeleteAttractionNotFound() {
		Long attractionId = 2L;
		when(attractionRepository.existsById(attractionId)).thenReturn(false);
		
		assertThrows(EntityDeletionException.class, () -> attractionService.deleteAttraction(attractionId));		
		
		verify(attractionRepository, times(1)).existsById(attractionId);
		verify(attractionRepository, times(0)).deleteById(attractionId);
	}
}
