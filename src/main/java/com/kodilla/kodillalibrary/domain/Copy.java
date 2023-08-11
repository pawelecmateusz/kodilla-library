package com.kodilla.kodillalibrary.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "COPIES")
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COPY_ID")
    private Long copyId;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    @NotNull
    private Book book;

    @OneToOne(targetEntity = Rent.class,
            mappedBy = "copy",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    private Rent rent;

    @Column(name = "STATUS")
    private String status;

}