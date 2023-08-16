package dev.jpa.movie.index.controller;

import dev.jpa.movie.index.service.HomeService;
import dev.jpa.movie.movieInfo.domain.dto.MovieIndexDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeControllerTest {

    @Autowired
    private HomeController homeController;

    @Autowired
    private  HomeService homeService;

    @Test
    public void test() {

        List<MovieIndexDto> result =  homeService.findMovieIndexDto(LocalDateTime.now().minus(1, ChronoUnit.DAYS));
        System.out.println(LocalDateTime.now().minus(1, ChronoUnit.DAYS));
        for (MovieIndexDto movieIndexDto : result) {
            System.out.println(movieIndexDto.toString());
        }

    }
}