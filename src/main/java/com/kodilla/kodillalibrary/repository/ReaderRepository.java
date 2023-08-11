package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {

    @Override
    Reader save(Reader reader);

    @Override
    Optional<Reader> findById(Long readerId);

    @Override
    List<Reader> findAll();

    @Override
    void deleteById(Long readerId);

}