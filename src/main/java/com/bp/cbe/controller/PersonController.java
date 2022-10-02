package com.bp.cbe.controller;

import com.bp.cbe.domain.dto.PersonDTO;
import com.bp.cbe.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController implements BaseController<PersonDTO, Long> {
    private final PersonService personService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonDTO data) {
        return new ResponseEntity<>(personService.create(data), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable Long id, @RequestBody @Valid PersonDTO data) {
        return ResponseEntity.ok(personService.update(id, data));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
