package dev.jpa.movie.reservation.domain.dto;

import lombok.Data;

@Data
public class ReservationInfo {

    private String time;
    private String movieCd;
    private String movieNm;
    private String seat;

    public ReservationInfo(String time, String movieCd, String movieNm, String seat) {
        this.time = time;
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.seat = seat;
    }

    public ReservationInfo() {
    }

    @Override
    public String toString() {
        return "ReservationInfo{" +
                "time='" + time + '\'' +
                ", movieCd='" + movieCd + '\'' +
                ", movieNm='" + movieNm + '\'' +
                ", seat='" + seat + '\'' +
                '}';
    }
}
