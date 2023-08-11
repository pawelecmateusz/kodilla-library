package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.domain.Rent;
import com.kodilla.kodillalibrary.domain.dto.RentDto;
import com.kodilla.kodillalibrary.mapper.RentMapper;
import com.kodilla.kodillalibrary.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/rents")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RentController {

    private final RentMapper rentMapper;
    private final RentService rentService;

    @GetMapping(value = "{rentId}")
    public ResponseEntity<RentDto> getRentById(@PathVariable Long rentId) {
        return ResponseEntity.ok(rentMapper.mapToRentDto(rentService.getRentById(rentId)));
    }

    @GetMapping
    public ResponseEntity<List<RentDto>> getAllRents() {
        return ResponseEntity.ok(rentMapper.mapToRentDtoList(rentService.getAllRents()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentDto> createRent(@RequestBody RentDto rentDto) {
        Rent rent = rentMapper.mapToRent(rentDto);
        rentService.rentABook(rent.getCopy(), rent.getReader());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{rentId}")
    public ResponseEntity<Object> returnRent(@PathVariable Long rentId) {
        rentService.returnABook(rentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{rentId}")
    public ResponseEntity<Void> deleteRent(@PathVariable Long rentId) {
        rentService.deleteRentById(rentId);
        return ResponseEntity.ok().build();
    }

}