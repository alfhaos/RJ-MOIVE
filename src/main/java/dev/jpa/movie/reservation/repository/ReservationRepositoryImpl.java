package dev.jpa.movie.reservation.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.jpa.movie.reservation.domain.dto.QUserReservationDto;
import dev.jpa.movie.reservation.domain.dto.UserReservationDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

import static dev.jpa.movie.movieInfo.domain.entity.QMovie.movie;
import static dev.jpa.movie.reservation.domain.entity.QMovieData.movieData;
import static dev.jpa.movie.reservation.domain.entity.QReservastionAccount.reservastionAccount;
import static dev.jpa.movie.reservation.domain.entity.QReservation.reservation;
import static dev.jpa.movie.user.domain.entity.QMember.member;

public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReservationRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserReservationDto> searchMemberReservationList(String memberId, String searchState) {
        return queryFactory
                .select(
                        new QUserReservationDto(
                                movie.movieNm,
                                reservation.updatedDate,
                                movieData.movieScreening.screeningTime,
                                reservastionAccount.payment,
                                movieData.movieScreening.seat,
                                reservation.delYn,
                                movie.movieCd,
                                reservation.id,
                                reservastionAccount.id))
                .from(reservation)
                .join(reservation.movie, movie) // Join with the movie entity
                .join(reservation.member, member) // Join with the member entity
                .join(reservation.movieDataList, movieData) // Join with the movieDataList entity
                .join(reservation.reservastionAccount, reservastionAccount) // Join with the reservastionAccount entity
                .where(
                        reservation.movie.movieCd.eq(movie.movieCd)
                                .and(reservation.member.id.eq(member.id))
                                .and(member.id.eq(memberId))
                                .and(reservationState(searchState))
                                .and(reservastionAccountState(searchState))
                )
                .fetch();
    }

    private BooleanExpression reservastionAccountState(String searchState) {
        if(searchState.equals("all")){
            return null;
        } else if(searchState.equals("complete")){
            return reservation.reservastionAccount.delYn.eq("N");
        } else {
            return reservation.reservastionAccount.delYn.eq("Y");
        }
    }

    private BooleanExpression reservationState(String searchState) {

        if(searchState.equals("all")){
            return null;
        } else if(searchState.equals("complete")){
            return reservation.delYn.eq("N");
        } else {
            return reservation.delYn.eq("Y");
        }

    }
}
