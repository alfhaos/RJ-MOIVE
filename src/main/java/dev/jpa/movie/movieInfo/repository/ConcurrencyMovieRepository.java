package dev.jpa.movie.movieInfo.repository;

import dev.jpa.movie.movieInfo.domain.embedded.ConcurrencyEmId;
import dev.jpa.movie.movieInfo.domain.entity.ConcurrencyMovie;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ConcurrencyMovieRepository extends JpaRepository<ConcurrencyMovie, ConcurrencyEmId> {

    @Lock(LockModeType.OPTIMISTIC)
    @Override
    Optional<ConcurrencyMovie> findById(ConcurrencyEmId concurrencyEm);

}
