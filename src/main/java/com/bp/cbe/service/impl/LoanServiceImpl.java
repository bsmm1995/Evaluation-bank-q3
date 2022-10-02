package com.bp.cbe.service.impl;

import com.bp.cbe.domain.dto.LoanDTO;
import com.bp.cbe.domain.entities.LoanEntity;
import com.bp.cbe.domain.entities.PersonEntity;
import com.bp.cbe.domain.enums.Status;
import com.bp.cbe.domain.enums.UserType;
import com.bp.cbe.exceptions.LoanException;
import com.bp.cbe.exceptions.NotFoundException;
import com.bp.cbe.repository.LoanRepository;
import com.bp.cbe.repository.PersonRepository;
import com.bp.cbe.service.LoanService;
import com.bp.cbe.service.PersonService;
import com.bp.cbe.utils.Constants;
import com.bp.cbe.utils.Date;
import com.bp.cbe.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final PersonService personService;
    private final PersonRepository personRepository;

    @Override
    public LoanDTO getById(Long id) {
        return this.toDto(this.getEntityById(id));
    }

    @Override
    public List<LoanDTO> getAll() {
        return loanRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional
    public LoanDTO create(Long personId, LoanDTO data) {
        LoanEntity entity = this.toEntity(data);
        entity.setLoanDate(LocalDate.now());

        UserType userType = personService.getUserTypeById(personId);
        int numberLoans = personService.getNumberLoansById(personId);

        if (((userType == UserType.EXTERNAL || userType == UserType.GUEST) && numberLoans >= 1) || (userType == UserType.INTERNAL && numberLoans >= 2)) {
            throwLoanException(personId);
        }

        this.validateDate(userType, entity);
        personService.updateStatus(personId, Status.ACTIVE);
        entity.setPerson(new PersonEntity(personId));
        return this.toDto(loanRepository.save(entity));
    }

    @Override
    @Transactional
    public LoanDTO update(Long id, LoanDTO data) {
        LoanEntity entity = this.getEntityById(id);
        entity.setEquipmentSeries(data.getEquipmentSeries());
        return this.toDto(loanRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        loanRepository.delete(this.getEntityById(id));
    }

    private LoanEntity getEntityById(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el registro con ID " + id));
    }

    private LoanEntity toEntity(LoanDTO data) {
        return Mapper.modelMapper().map(data, LoanEntity.class);
    }

    private LoanDTO toDto(LoanEntity entity) {
        return Mapper.modelMapper().map(entity, LoanDTO.class);
    }

    private void throwLoanException(Long personId) {
        throw new LoanException(String.format("The user with ID %d already has the maximum quota allowed and it is not possible to make another loan.", personId));
    }

    private void validateDate(UserType userType, LoanEntity entity) {
        LocalDate returnDate = LocalDate.now();

        if (userType == UserType.GUEST) {
            returnDate = Date.addDaysWithoutSaturdayAndSunday(Constants.MAX_DAYS_GUEST);
        }

        if (userType == UserType.INTERNAL) {
            returnDate = Date.addDays(Constants.CALENDAR_YEAR);
        }

        if (userType == UserType.EXTERNAL) {
            returnDate = Date.addDays(Constants.BUSINESS_YEAR);
        }

        if (entity.getReturnDate() == null) {
            entity.setReturnDate(returnDate);
        } else if (returnDate != entity.getReturnDate()) {
            throw new LoanException("Error in return date for user type " + userType);
        }
    }
}
