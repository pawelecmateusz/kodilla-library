package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends CrudRepository<Rent, Long> {

}