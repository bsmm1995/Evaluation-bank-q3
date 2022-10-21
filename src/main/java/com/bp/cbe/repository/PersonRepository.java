package com.bp.cbe.repository;

import com.bp.cbe.domain.dto.IReport;
import com.bp.cbe.domain.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT PE.NAMES AS userNames, PE.TYPE AS userType, LO.EQUIPMENT_SERIES AS equipmentSeries, LO.LOAN_DATE AS loanDate, LO.RETURN_DATE AS returnDate " + "FROM PERSON PE " + "INNER JOIN LOAN LO ON LO.PERSON_ID = PE.ID " + "WHERE PE.ID = :personId")
    List<IReport> generateReportByPersonId(Long personId);
}
