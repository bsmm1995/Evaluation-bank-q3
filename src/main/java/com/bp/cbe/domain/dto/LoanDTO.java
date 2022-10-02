package com.bp.cbe.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanDTO implements Serializable {
    Long id;

    @NotBlank
    String equipmentSeries;

    LocalDate loanDate;

    @NotNull
    LocalDate returnDate;
    PersonOutDTO person;
}
