package dev.jpa.movie.movieInfo.domain.dto;

import dev.jpa.movie.movieInfo.domain.embedded.ConcurrencyEmId;
import dev.jpa.movie.reservation.domain.type.SeatState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConcurrencyMovieDto {

    private ConcurrencyEmId concurrencyEmId;
    private SeatState state;

}
