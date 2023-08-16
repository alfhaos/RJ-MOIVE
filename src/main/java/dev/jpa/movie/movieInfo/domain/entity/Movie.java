package dev.jpa.movie.movieInfo.domain.entity;

import dev.jpa.movie.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(of = "movieCd")
public class Movie extends BaseEntity {

    private String boxofficeType;
    private String showRange;
    private String rnum;
    private String rank;
    private String rankInten;
    private String rankOldAndNew;
    @Id
    private String movieCd;
    private String movieNm;
    private String openDt;
    private String salesAmt;
    private String salesShare;
    private String salesInten;
    private String salesChange;
    private String salesAcc;
    private String audiCnt;
    private String audiInten;
    private String audiChange;
    private String audiAcc;
    private String scrnCnt;
    private String showCnt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<ConcurrencyMovie> concurrencyMovie = new ArrayList<>();

    public Movie(String movieCd) {
        this.movieCd = movieCd;
    }

    public Movie(String boxofficeType, String showRange, String rnum, String rank, String rankInten, String rankOldAndNew, String movieCd, String movieNm, String openDt, String salesAmt, String salesShare, String salesInten, String salesChange, String salesAcc, String audiCnt, String audiInten, String audiChange, String audiAcc, String scrnCnt, String showCnt) {
        this.boxofficeType = boxofficeType;
        this.showRange = showRange;
        this.rnum = rnum;
        this.rank = rank;
        this.rankInten = rankInten;
        this.rankOldAndNew = rankOldAndNew;
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.salesAmt = salesAmt;
        this.salesShare = salesShare;
        this.salesInten = salesInten;
        this.salesChange = salesChange;
        this.salesAcc = salesAcc;
        this.audiCnt = audiCnt;
        this.audiInten = audiInten;
        this.audiChange = audiChange;
        this.audiAcc = audiAcc;
        this.scrnCnt = scrnCnt;
        this.showCnt = showCnt;
    }

    public Movie() {

    }
}
