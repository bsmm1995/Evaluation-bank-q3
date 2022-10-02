package com.bp.cbe.repository.dao.impl;

import com.bp.cbe.domain.dto.ReportDTO;
import com.bp.cbe.domain.entities.LoanEntity;
import com.bp.cbe.domain.entities.PersonEntity;
import com.bp.cbe.repository.dao.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

@Repository
@AllArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {
    private final EntityManager entityManager;

    @Override
    public List<ReportDTO> getReportData(Long personId, LocalDate loanDate, LocalDate returnDate) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ReportDTO> query = criteriaBuilder.createQuery(ReportDTO.class);

        Root<LoanEntity> root = query.from(LoanEntity.class);
        Join<LoanEntity, PersonEntity> person = root.join("person", JoinType.INNER);

        query.multiselect(
                root.get("names").alias("user"),
                root.get("userType").alias("userType"),
                root.get("equipmentSeries").alias("equipmentSeries"),
                root.get("loanDate").alias("loanDate"),
                root.get("returnDate").alias("returnDate"));

        query.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(person.get("id"), personId),
                        criteriaBuilder.equal(root.get("loanDate"), 0.75),
                        criteriaBuilder.equal(root.get("returnDate"), 0.75)));

        query.orderBy(criteriaBuilder.desc(root.get("loanDate")));

        TypedQuery<ReportDTO> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }
}
