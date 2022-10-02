package com.bp.cbe.service.impl;

import com.bp.cbe.domain.dto.PersonDTO;
import com.bp.cbe.domain.entities.PersonEntity;
import com.bp.cbe.exceptions.NotFoundException;
import com.bp.cbe.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    PersonRepository personRepositoryMock;

    @InjectMocks
    PersonServiceImpl personService;

    PersonDTO dto;
    PersonEntity entity;

    Long id;

    @BeforeEach
    void setUp() {
        id = 1L;
        dto = new PersonDTO();
        dto.setId(id);
        dto.setNames("Bladimir");

        entity = new PersonEntity();
        entity.setId(id);
        entity.setNames("Bladimir");
    }

    @Test
    void getById() {
        when(personRepositoryMock.findById(id)).thenReturn(Optional.of(entity));
        PersonDTO result = personService.getById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getAll() {
        when(personRepositoryMock.findAll()).thenReturn(List.of(entity));
        List<PersonDTO> result = personService.getAll();
        assertNotNull(result);
        assertEquals(id, result.size());
    }

    @Test
    void create() {
        when(personRepositoryMock.save(any())).thenReturn(entity);
        PersonDTO result = personService.create(dto);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void update() {
        when(personRepositoryMock.findById(id)).thenReturn(Optional.of(entity));
        when(personRepositoryMock.save(any())).thenReturn(entity);
        PersonDTO result = personService.update(id, dto);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void personNotFound() {
        when(this.personRepositoryMock.findById(id)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(NotFoundException.class, () -> personService.getById(id));
        assertEquals("No se encontró el registro con ID " + id, exception.getMessage());
    }
}