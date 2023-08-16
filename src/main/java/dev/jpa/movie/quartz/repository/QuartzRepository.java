package dev.jpa.movie.quartz.repository;

import dev.jpa.movie.movieInfo.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuartzRepository extends JpaRepository<Movie, String> {
}
