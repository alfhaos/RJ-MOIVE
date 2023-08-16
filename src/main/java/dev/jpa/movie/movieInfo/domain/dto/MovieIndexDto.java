package dev.jpa.movie.movieInfo.domain.dto;

import lombok.Data;

@Data
public class MovieIndexDto {

    private String movieCd;
    private String movieNm;
    private String rank;
    private String openDt;
    private String audiAcc;

    public MovieIndexDto(String movieCd, String movieNm, String rank, String openDt, String audiAcc) {
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.rank = rank;
        this.openDt = openDt;
        this.audiAcc = audiAcc;
    }
}
