package dev.jpa.movie.index.repositroy;

import dev.jpa.movie.movieInfo.domain.dto.MovieIndexDto;
import dev.jpa.movie.movieInfo.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HomeRepository extends JpaRepository<Movie, String>, HomeRepositoryCustom {

    @Query("select new dev.jpa.movie.movieInfo.domain.dto.MovieIndexDto(m.movieCd, m.movieNm, m.rank, m.openDt, m.audiAcc) " +
            "from Movie m ")
    List<MovieIndexDto> findMovieIndexDto(@Param("createDate") LocalDateTime date);
}
