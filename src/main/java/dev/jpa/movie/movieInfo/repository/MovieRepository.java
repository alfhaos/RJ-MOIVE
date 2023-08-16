package dev.jpa.movie.movieInfo.repository;

import dev.jpa.movie.movieInfo.domain.dto.SeatListDto;
import dev.jpa.movie.movieInfo.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, String> {

    @Query("select new dev.jpa.movie.movieInfo.domain.dto.SeatListDto(md.movieScreening.seat) " +
            "from MovieData md join Reservation r " +
            "on md.reservation.id = r.id " +
            "where md.movieScreening.screeningTime = :time " +
            "and r.movie.movieCd = :movieCd " +
            "and r.delYn in ('N') ")
    List<SeatListDto> findSeatList(@Param("time") String time, @Param("movieCd") String movieCd);
}
