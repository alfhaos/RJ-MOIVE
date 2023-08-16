package dev.jpa.movie.reservation.domain.dto;

import dev.jpa.movie.reservation.domain.entity.MovieData;
import dev.jpa.movie.reservation.domain.entity.MovieScreening;
import dev.jpa.movie.reservation.domain.entity.Reservation;
import dev.jpa.movie.reservation.domain.type.SeatState;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDataAccountDto {

    private Long id;
    private Reservation reservation;
    private MovieScreening movieScreening;

}
