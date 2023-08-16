package dev.jpa.movie.movieInfo.controller;

import dev.jpa.movie.movieInfo.domain.dto.SeatListDto;
import dev.jpa.movie.movieInfo.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieInfoController {

    private final MovieRepository movieRepository;

    @GetMapping("/api/movieInfo/seatList")
    public List<SeatListDto> findSeatList(@RequestParam(value = "time") String time,
                                          @RequestParam(value = "movieCd") String movieCd) {

        return movieRepository.findSeatList(time,movieCd);
    }
}
