package com.bp.cbe.repository.dao;

import com.bp.cbe.domain.dto.ReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository {
    List<ReportDTO> getReportData(Long personId, LocalDate loanDate, LocalDate returnDate);
}
