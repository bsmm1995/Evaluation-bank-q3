package com.bp.cbe.domain.dto;

import com.bp.cbe.domain.enums.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportDTO implements Serializable {
    String user;
    UserType userType;
    String equipmentSeries;
    LocalDate loanDate;
    LocalDate returnDate;
}
