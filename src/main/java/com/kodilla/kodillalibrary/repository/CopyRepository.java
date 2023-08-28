package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {

}