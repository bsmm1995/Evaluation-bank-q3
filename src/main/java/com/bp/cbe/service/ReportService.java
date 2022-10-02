package com.bp.cbe.service;

import com.bp.cbe.domain.dto.ReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<ReportDTO> getReport(Long personId, LocalDate loanDate, LocalDate returnDate);
}
