package dev.jpa.movie.index.service;

import dev.jpa.movie.index.repositroy.HomeRepository;
import dev.jpa.movie.movieInfo.domain.dto.MovieIndexDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeRepository homeRepository;

    public List<MovieIndexDto> findMovieIndexDto(LocalDateTime dateTime) {
        return homeRepository.findMovieIndexDto(dateTime);
    }

    public List<MovieIndexDto> searchIndexList(String handleParam) {
        return homeRepository.searchIndexList(handleParam);
    }
}
