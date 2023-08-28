package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.controller.exceptions.CopyCurrentlyRentedException;
import com.kodilla.kodillalibrary.controller.exceptions.CopyNotFoundException;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.repository.CopyRepository;
import com.kodilla.kodillalibrary.repository.RentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CopyService {

    private final CopyRepository copyRepository;

    private final RentRepository rentRepository;

    @Autowired
    public CopyService(CopyRepository copyRepository, RentRepository rentRepository) {
        this.copyRepository = copyRepository;
        this.rentRepository = rentRepository;
    }

    @Transactional
    public Copy saveCopy(final Copy copy) {
        return copyRepository.save(copy);
    }

    public Copy getCopyById(final Long copyId) throws CopyNotFoundException {
        return copyRepository.findById(copyId).orElseThrow(CopyNotFoundException::new);
    }

    public List<Copy> getAllCopies() {
        return (List<Copy>) copyRepository.findAll();
    }

    @Transactional
    public void deleteCopyById(final Long copyId) throws CopyNotFoundException, CopyCurrentlyRentedException {

        Copy copy = copyRepository.findById(copyId).orElseThrow(CopyNotFoundException::new);
        if (copy.getRent() != null) {
            if ("available".equals(copy.getStatus())) {
                Long rent = copy.getRent().getRentId();
                copy.setRent(null);
                copy.getBook().getCopies().remove(copy);
                rentRepository.deleteById(rent);
                copyRepository.deleteById(copy.getCopyId());
            } else {
                throw new CopyCurrentlyRentedException();
            }
        } else {
            if ("available".equals(copy.getStatus())) {
                copyRepository.deleteById(copy.getCopyId());
            }
        }
    }

    @Transactional
    public void updateCopyStatus(Long copyId, String rented) throws CopyNotFoundException {

        Copy copy = copyRepository.findById(copyId).orElseThrow(CopyNotFoundException::new);
        copy.setStatus(rented);
        copyRepository.save(copy);
    }

}