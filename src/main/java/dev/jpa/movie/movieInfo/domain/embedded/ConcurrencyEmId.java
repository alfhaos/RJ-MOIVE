package dev.jpa.movie.movieInfo.domain.embedded;

import dev.jpa.movie.movieInfo.domain.entity.Movie;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class ConcurrencyEmId implements Serializable {

    @Column(name = "SCEENING_TIME")
    private String sceeningTime;

    @Column(name = "SEAT")
    private String seat;

    private String movieCd;

    @Override
    public String toString() {
        return "ConcurrencyEmId{" +
                "sceeningTime='" + sceeningTime + '\'' +
                ", seat='" + seat + '\'' +
                ", movieCd='" + movieCd + '\'' +
                '}';
    }
}
