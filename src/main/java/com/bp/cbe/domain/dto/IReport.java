package com.bp.cbe.domain.dto;

import com.bp.cbe.domain.enums.UserType;

import java.time.LocalDate;

public interface IReport {
    String getUserNames();

    UserType getUserType();

    String getEquipmentSeries();

    LocalDate getLoanDate();

    LocalDate getReturnDate();
}