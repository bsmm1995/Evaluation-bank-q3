package com.bp.cbe.service;

import com.bp.cbe.domain.dto.PersonDTO;
import com.bp.cbe.domain.enums.Status;
import com.bp.cbe.domain.enums.UserType;

public interface PersonService extends BaseService<PersonDTO, Long> {
    void updateStatus(Long id, Status status);

    UserType getUserTypeById(Long id);

    int getNumberLoansById(Long id);

    PersonDTO create(PersonDTO data);
}
