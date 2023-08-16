package dev.jpa.movie.reservation.domain.entity;

import dev.jpa.movie.common.entity.BaseEntity;
import dev.jpa.movie.movieInfo.domain.entity.Movie;
import dev.jpa.movie.user.domain.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString(of = {"id", "movie", "member", "movieDataList"})
@Getter
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_cd")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "reservation",fetch = FetchType.LAZY)
    private List<MovieData> movieDataList = new ArrayList<>();

    @Column(name = "del_yn", columnDefinition = "VARCHAR(1) default 'N'")
    private String delYn = "N";

    @OneToOne(mappedBy = "reservation")
    private ReservastionAccount reservastionAccount;

    //테스트용
    public Reservation(Movie movie){
        this.movie = movie;
    }

    public void addMember(Member member){
        this.member = member;
        member.getReservation().add(this);
    }

    public void updateDelYn(String delYn) {
        this.delYn = delYn;
    }
}
