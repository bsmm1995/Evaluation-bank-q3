package com.bp.cbe.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "LOAN")
public class LoanEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    Long id;

    @Column(name = "EQUIPMENT_SERIES", nullable = false)
    String equipmentSeries;

    @Column(name = "LOAN_DATE", nullable = false)
    LocalDate loanDate;

    @Column(name = "RETURN_DATE", nullable = false)
    LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID", nullable = false, insertable = false, updatable = false)
    private PersonEntity person;
}
