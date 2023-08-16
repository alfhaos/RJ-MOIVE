package dev.jpa.movie.reservation.domain.entity;

import dev.jpa.movie.common.entity.BaseEntity;
import dev.jpa.movie.reservation.domain.type.SeatState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;


@Entity
@ToString(of = {"id", "state", "movieScreening"})
@Getter
@DynamicUpdate
public class MovieData extends BaseEntity {

    @Id
    @Column(name = "movie_data_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Embedded
    private MovieScreening movieScreening;

    public MovieData() {
    }

    public MovieData(MovieScreening movieScreening) {
        this.movieScreening = movieScreening;
    }

    public void addReservation(Reservation reservation) {
        this.reservation = reservation;
        reservation.getMovieDataList().add(this);
    }
}
