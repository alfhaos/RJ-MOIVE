package dev.jpa.movie.reservation.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class UserReservationDto{
    private String movieNm;
    private LocalDateTime reservationDate;
    private String screeningTime;
    private String price;
    private String seat;
    private String delYn;

    public UserReservationDto(String movieNm, LocalDateTime reservationDate, String screeningTime, String price, String seat, String delYn) {
        this.movieNm = movieNm;
        this.reservationDate = reservationDate;
        this.screeningTime = screeningTime;
        this.price = price;
        this.seat = seat;
        this.delYn = delYn;
    }
}
