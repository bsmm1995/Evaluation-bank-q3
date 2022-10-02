package com.bp.cbe.domain.dto;

import com.bp.cbe.domain.enums.Status;
import com.bp.cbe.domain.enums.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonOutDTO implements Serializable {
    Long id;
    String names;
    UserType type;
    Status status;
}
