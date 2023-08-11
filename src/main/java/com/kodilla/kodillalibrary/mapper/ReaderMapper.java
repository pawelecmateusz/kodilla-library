package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.dto.ReaderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderMapper {

    public Reader mapToReader(final ReaderDto readerDto) {
        return Reader.builder()
                .readerId(readerDto.getReaderId())
                .firstname(readerDto.getFirstname())
                .lastname(readerDto.getLastname())
                .joinDate(readerDto.getJoinDate())
                .build();
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getReaderId(),
                reader.getFirstname(),
                reader.getLastname(),
                reader.getJoinDate());
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readers) {
        return readers.stream()
                .map(this::mapToReaderDto)
                .toList();
    }

}