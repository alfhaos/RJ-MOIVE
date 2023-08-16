package dev.jpa.movie.reservation.domain.dto;

import lombok.Data;

@Data
public class ReservationListDto {
    private String movieName;
    private String seat;
    private String screeningTime;

    public ReservationListDto(String movieName, String seat, String screeningTime) {
        this.movieName = movieName;
        this.seat = seat;
        this.screeningTime = screeningTime;
    }

    public ReservationListDto(String movieName) {
        this.movieName = movieName;
    }
}
