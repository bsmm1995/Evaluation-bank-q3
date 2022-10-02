package com.bp.cbe.service;

import com.bp.cbe.domain.dto.PersonDTO;

public interface PersonService extends BaseService<PersonDTO, Long> {
    PersonDTO create(PersonDTO data);
}
