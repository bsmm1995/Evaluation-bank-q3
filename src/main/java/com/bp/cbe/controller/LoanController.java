package com.bp.cbe.controller;

import com.bp.cbe.domain.dto.LoanDTO;
import com.bp.cbe.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController implements BaseController<LoanDTO, Long> {
    private final LoanService loanService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAll() {
        return ResponseEntity.ok(loanService.getAll());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> update(@PathVariable Long id, @RequestBody @Valid LoanDTO data) {
        return ResponseEntity.ok(loanService.update(id, data));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        loanService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
