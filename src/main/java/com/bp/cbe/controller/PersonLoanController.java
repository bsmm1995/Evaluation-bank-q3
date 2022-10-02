package com.bp.cbe.controller;

import com.bp.cbe.domain.dto.LoanDTO;
import com.bp.cbe.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonLoanController {

    private final LoanService loanService;

    @PostMapping("/{person-id}/loans")
    public ResponseEntity<LoanDTO> create(@PathVariable(name = "person-id") Long personId, @RequestBody @Valid LoanDTO data) {
        return ResponseEntity.ok(loanService.create(personId, data));
    }
}
