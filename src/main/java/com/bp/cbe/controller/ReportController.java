package com.bp.cbe.controller;

import com.bp.cbe.domain.dto.ReportDTO;
import com.bp.cbe.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{person-id}/json")
    public ResponseEntity<List<ReportDTO>> getReport(@PathVariable(name = "person-id") Long personId,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate loanDate,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate returnDate) {
        return ResponseEntity.ok(reportService.getReport(personId, loanDate, returnDate));
    }
}
