package com.bp.cbe.domain.dto;

import com.bp.cbe.domain.enums.Status;
import com.bp.cbe.domain.enums.UserType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDTO implements Serializable {
    Long id;

    @NotBlank String names;

    @NotNull UserType type;

    Status status;
    List<LoanOutDTO> loans;

    public PersonDTO(Long id, String names, UserType type) {
        this.id = id;
        this.names = names;
        this.type = type;
    }
}
