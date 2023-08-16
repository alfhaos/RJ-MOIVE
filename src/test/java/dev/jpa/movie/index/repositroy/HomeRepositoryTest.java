package dev.jpa.movie.index.repositroy;

import dev.jpa.movie.index.controller.HomeController;
import dev.jpa.movie.movieInfo.domain.dto.MovieIndexDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
class HomeRepositoryTest {

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private HomeController controller;

    @Test
    public void homeTest() {

        List<MovieIndexDto> result = homeRepository.findMovieIndexDto(LocalDateTime.now().minus(1, ChronoUnit.DAYS));

        for (MovieIndexDto movieIndexDto : result) {
            System.out.println(movieIndexDto.getMovieNm());
        }

    }

}