package com.kodilla.kodillalibrary.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "RENTS")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rentId;

    @OneToOne
    @JoinColumn(name = "COPY_ID")
    //@NotNull
    private Copy copy;

    @ManyToOne
    @JoinColumn(name = "READER_ID")
    //@NotNull
    private Reader reader;

    @NotNull
    @Column(name = "RENT_DATE")
    private LocalDate rentDate;

    @Column(name = "RETURN_DATE")
    private LocalDate returnDate;

}