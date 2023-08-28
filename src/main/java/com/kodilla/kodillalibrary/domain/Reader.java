package com.kodilla.kodillalibrary.domain;

import com.kodilla.kodillalibrary.controller.exceptions.ReaderHasRentsException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "READERS")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "READER_ID")
    private Long readerId;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "JOIN_DATE")
    private LocalDate joinDate;

    @OneToMany(targetEntity = Rent.class,
            mappedBy = "reader",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    @Builder.Default
    public List<Rent> rents = new ArrayList<>();

    public boolean hasAnyRents() throws ReaderHasRentsException {
        if (rents.size() > 0) {
            throw new ReaderHasRentsException();
        }
        return false;
    }

}