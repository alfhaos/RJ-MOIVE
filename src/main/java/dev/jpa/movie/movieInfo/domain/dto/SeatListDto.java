package dev.jpa.movie.movieInfo.domain.dto;

import dev.jpa.movie.reservation.domain.type.SeatState;
import lombok.Data;

@Data
public class SeatListDto {

    private String seat;

    public SeatListDto(String seat) {
        this.seat = seat;
    }

    public SeatListDto() {
    }
}
