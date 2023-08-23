package dev.jpa.movie.reservation.repository;

import dev.jpa.movie.reservation.domain.dto.UserReservationDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepositoryCustom {

    List<UserReservationDto> searchMemberReservationList(String memberId, String searchState);
}
