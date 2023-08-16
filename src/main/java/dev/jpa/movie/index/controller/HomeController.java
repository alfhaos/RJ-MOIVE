package dev.jpa.movie.index.controller;

import dev.jpa.movie.index.service.HomeService;
import dev.jpa.movie.movieInfo.domain.dto.MovieIndexDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/api/movieList")
    public List<MovieIndexDto> findAll(){
        return homeService.findMovieIndexDto(LocalDateTime.now().minus(1, ChronoUnit.DAYS));
    }
}
