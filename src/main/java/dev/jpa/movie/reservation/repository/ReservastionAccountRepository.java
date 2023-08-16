package dev.jpa.movie.reservation.repository;

import dev.jpa.movie.reservation.domain.entity.ReservastionAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservastionAccountRepository extends JpaRepository<ReservastionAccount, String> {
}
