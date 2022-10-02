package com.bp.cbe.controller;

import com.bp.cbe.domain.dto.ReportDTO;
import com.bp.cbe.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{person-id}")
    public ResponseEntity<List<ReportDTO>> getReport(@PathVariable(name = "person-id") Long personId,
                                                     @RequestParam LocalDate loanDate,
                                                     @RequestParam LocalDate returnDate) {
        return ResponseEntity.ok(reportService.getReport(personId, loanDate, returnDate));
    }
}
