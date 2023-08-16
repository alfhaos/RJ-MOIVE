package dev.jpa.movie.reservation.repository;

import dev.jpa.movie.reservation.domain.entity.MovieData;
import jakarta.annotation.Nullable;
import jakarta.persistence.LockModeType;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface MovieDataRepository extends JpaRepository<MovieData, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Override
    <S extends MovieData> S save(S entity);

    @Nullable
    @Query("select MD from Reservation R join MovieData MD " +
            "ON R.id = MD.id " +
            "where " +
            "R.movie.movieCd = :movieCd " +
            "and MD.movieScreening.seat = :seat " +
            "and MD.movieScreening.screeningTime = :time ")
    MovieData findDataForLock(@Param("movieCd") String movieCd, @Param("seat") String seat, @Param("time") String time);
}
