package com.bp.cbe.service.impl;

import com.bp.cbe.domain.dto.PersonDTO;
import com.bp.cbe.domain.entities.PersonEntity;
import com.bp.cbe.domain.enums.Status;
import com.bp.cbe.domain.enums.UserType;
import com.bp.cbe.exceptions.NotFoundException;
import com.bp.cbe.repository.PersonRepository;
import com.bp.cbe.service.PersonService;
import com.bp.cbe.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public PersonDTO getById(Long id) {
        return this.toDto(this.getEntityById(id));
    }

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional
    public PersonDTO create(PersonDTO data) {
        PersonEntity entity = this.toEntity(data);
        if (entity.getStatus() == null) {
            entity.setStatus(Status.CREATED);
        }
        return toDto(personRepository.save(entity));
    }

    @Override
    @Transactional
    public PersonDTO update(Long id, PersonDTO data) {
        PersonEntity entity = this.getEntityById(id);
        entity.setType(data.getType());
        entity.setStatus(data.getStatus());
        return this.toDto(personRepository.save(entity));
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Status status) {
        PersonEntity entity = this.getEntityById(id);
        entity.setStatus(status);
        personRepository.save(entity);
    }

    @Override
    public UserType getUserTypeById(Long id) {
        return this.getEntityById(id).getType();
    }

    @Override
    public int getNumberLoansById(Long id) {
        return this.getEntityById(id).getLoans().size();
    }

    @Override
    public void deleteById(Long id) {
        personRepository.delete(this.getEntityById(id));
    }

    private PersonEntity getEntityById(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Record with ID %d not found", id)));
    }

    private PersonEntity toEntity(PersonDTO data) {
        return Mapper.modelMapper().map(data, PersonEntity.class);
    }

    private PersonDTO toDto(PersonEntity entity) {
        return Mapper.modelMapper().map(entity, PersonDTO.class);
    }
}
