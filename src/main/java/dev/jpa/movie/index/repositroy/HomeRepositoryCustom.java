package dev.jpa.movie.index.repositroy;

import dev.jpa.movie.movieInfo.domain.dto.MovieIndexDto;

import java.util.List;

public interface HomeRepositoryCustom {

    List<MovieIndexDto> searchIndexList(String handleParam);
}
