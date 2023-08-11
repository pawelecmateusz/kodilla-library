package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.dto.ReaderDto;
import com.kodilla.kodillalibrary.mapper.ReaderMapper;
import com.kodilla.kodillalibrary.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/readers")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderMapper readerMapper;
    private final ReaderService readerService;

    @GetMapping(value = "{readerId}")
    public ResponseEntity<ReaderDto> getReaderById(@PathVariable Long readerId) {
        return ResponseEntity.ok(readerMapper.mapToReaderDto(readerService.getReaderById(readerId)));
    }

    @GetMapping
    public ResponseEntity<List<ReaderDto>> getAllReaders() {
        return ResponseEntity.ok(readerMapper.mapToReaderDtoList(readerService.getAllReaders()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReaderDto> createReader(@RequestBody ReaderDto readerDto) {
        Reader reader = readerMapper.mapToReader(readerDto);
        readerService.saveReader(reader);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{readerId}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long readerId) {
        readerService.deleteReaderById(readerId);
        return ResponseEntity.ok().build();
    }

}