package com.bp.cbe.service.impl;

import com.bp.cbe.domain.dto.ReportDTO;
import com.bp.cbe.repository.PersonRepository;
import com.bp.cbe.repository.dao.ReportRepository;
import com.bp.cbe.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final PersonRepository personRepository;

    @Override
    public List<ReportDTO> getReport(Long personId, LocalDate loanDate, LocalDate returnDate) {
        return reportRepository.getReportData(personId, loanDate, returnDate);
    }

    @Override
    public List<ReportDTO> getReportByPersonId(Long personId) {
        return personRepository.generateReportByPersonId(personId)
                .stream()
                .map(
                        e -> new ReportDTO(e.getUserNames(), e.getUserType(), e.getEquipmentSeries(), e.getLoanDate(), e.getReturnDate())
                ).toList();
    }
}
