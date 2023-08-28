package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query
    int countAvailableCopies(@Param("bookId") Long bookId);

}