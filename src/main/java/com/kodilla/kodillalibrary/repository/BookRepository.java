package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Query
    int countAvailableCopies(@Param("bookId") Long bookId);

    @Override
    Book save(Book book);

    @Override
    Optional<Book> findById(Long bookId);

    @Override
    List<Book> findAll();

    @Override
    void deleteById(Long bookId);

}