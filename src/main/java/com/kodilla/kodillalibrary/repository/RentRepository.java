package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends CrudRepository<Rent, Long> {

    @Override
    Rent save(Rent rent);

    @Override
    Optional<Rent> findById(Long rentId);

    @Override
    List<Rent> findAll();

    @Override
    void deleteById(Long rentId);

}