package dev.jpa.movie.reservation.repository;

import dev.jpa.movie.reservation.domain.dto.ReservationListDto;
import dev.jpa.movie.reservation.domain.dto.UserReservationDto;
import dev.jpa.movie.reservation.domain.entity.Reservation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long reservationId);

    //oneToMany 관계는 join에서 on 절 사용 불가능
    @Query("select new dev.jpa.movie.reservation.domain.dto.UserReservationDto(M.movieNm,R.updatedDate,MD.movieScreening.screeningTime,RA.payment, MD.movieScreening.seat, R.delYn) " +
            "from Reservation R join Movie M " +
            "on R.movie.movieCd = M.movieCd " +
            "join Member MB " +
            "on R.member.id = MB.id " +
            "join R.movieDataList MD " +
            "join R.reservastionAccount RA " +
            "where MB.id = :memberId ")
    List<UserReservationDto> findMemberReservationList(@Param("memberId") String memberId);
}
