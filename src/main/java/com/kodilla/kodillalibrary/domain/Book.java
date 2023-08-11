package com.kodilla.kodillalibrary.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NamedQuery(
        name = "Book.countAvailableCopies",
        query = "SELECT COUNT(*) FROM Copy CP WHERE CP.book.id = :bookId AND CP.status = 'available'"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOOK_ID")
    private Long bookId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PUB_YEAR")
    private int pubYear;

    @OneToMany(targetEntity = Copy.class,
            mappedBy = "book",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    @Builder.Default
    private List<Copy> copies = new ArrayList<>();

}