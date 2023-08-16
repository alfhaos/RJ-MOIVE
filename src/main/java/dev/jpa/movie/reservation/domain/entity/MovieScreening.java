package dev.jpa.movie.reservation.domain.entity;

import dev.jpa.movie.common.entity.BaseEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Embeddable
@Getter
@ToString(of = {"screeningTime", "seat"})
public class MovieScreening {

    //상영 시간
    private String screeningTime;

    //좌석
    private String seat;

    public MovieScreening() {
    }

    public MovieScreening(String screeningTime, String seat) {
        this.screeningTime = screeningTime;
        this.seat = seat;
    }
}
