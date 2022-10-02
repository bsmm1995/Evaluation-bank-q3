package com.bp.cbe.domain.entities;

import com.bp.cbe.domain.enums.Status;
import com.bp.cbe.domain.enums.UserType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "PERSON")
@NoArgsConstructor
public class PersonEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    Long id;

    @Column(nullable = false)
    String names;

    @Column(nullable = false)
    UserType type;

    @Column(nullable = false)
    Status status = Status.CREATED;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<LoanEntity> loans;

    public PersonEntity(Long id) {
        this.id = id;
    }
}
