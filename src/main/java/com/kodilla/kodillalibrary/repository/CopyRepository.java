package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {

    @Override
    Copy save(Copy copy);

    @Override
    Optional<Copy> findById(Long copyId);

    @Override
    List<Copy> findAll();

    @Override
    void deleteById(Long copyId);

}