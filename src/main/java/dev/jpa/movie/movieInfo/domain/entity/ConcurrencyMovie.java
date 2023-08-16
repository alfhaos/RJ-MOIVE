package dev.jpa.movie.movieInfo.domain.entity;

import dev.jpa.movie.common.entity.BaseEntity;
import dev.jpa.movie.movieInfo.domain.embedded.ConcurrencyEmId;
import dev.jpa.movie.reservation.domain.type.SeatState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.jpa.repository.Lock;

@Entity
@Getter
@Setter
@ToString(of = {"id", "movie"})
@Table(name = "CONCURRENCY_MOVIE")
public class ConcurrencyMovie extends BaseEntity {

    @EmbeddedId
    private ConcurrencyEmId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieCd") // 연관된 movie의 cd를 참조
    @JoinColumn(name = "movieCd")
    private Movie movie;

    private String seatState;

    @Version
    private Long version;

    public ConcurrencyMovie() {

    }

    public ConcurrencyMovie(ConcurrencyEmId id) {
        this.id = id;
    }

    public void addMovie(Movie movie) {
        this.movie = movie;
        movie.getConcurrencyMovie().add(this);
    }

}
