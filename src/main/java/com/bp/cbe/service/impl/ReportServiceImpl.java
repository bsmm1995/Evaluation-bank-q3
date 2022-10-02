package com.bp.cbe.service.impl;

import com.bp.cbe.domain.dto.ReportDTO;
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

    @Override
    public List<ReportDTO> getReport(Long personId, LocalDate loanDate, LocalDate returnDate) {
        return reportRepository.getReportData(personId, loanDate, returnDate);
    }
}
