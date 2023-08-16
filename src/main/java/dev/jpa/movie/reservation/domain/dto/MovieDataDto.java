package dev.jpa.movie.reservation.domain.dto;

import dev.jpa.movie.reservation.domain.entity.MovieScreening;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDataDto {

    private Long id;
    private String state;
    private MovieScreening movieScreening;

}
