package dev.jpa.movie.reservation.repository;

import dev.jpa.movie.movieInfo.domain.entity.Movie;
import dev.jpa.movie.movieInfo.repository.MovieRepository;
import dev.jpa.movie.reservation.domain.dto.ReservationListDto;
import dev.jpa.movie.reservation.domain.dto.UserReservationDto;
import dev.jpa.movie.reservation.domain.entity.MovieData;
import dev.jpa.movie.reservation.domain.entity.MovieScreening;
import dev.jpa.movie.reservation.domain.entity.Reservation;
import dev.jpa.movie.reservation.domain.type.SeatState;
import dev.jpa.movie.user.domain.entity.Member;
import dev.jpa.movie.user.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieDataRepository movieDataRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void test() {

    }
    @Test
    @Transactional
    // 멤버 및 예약 저장 후 READ
    public void ReservationTest() {
/*
        // 회원 저장 및 조회
        Member member = new Member("test2","홍길동2", "010-1234-5678", "123456");
        Member saveMember = memberRepository.save(member);

        em.flush();
        em.clear();

        Movie movie = movieRepository.findById("20228930").get();

        // 상영정보 저장
        List<MovieData> movieDataList = new ArrayList<>();
        MovieScreening movieScreening = new MovieScreening("11:30","C010");
        MovieScreening movieScreening2 = new MovieScreening("11:30","C011");

        MovieData movieData = new MovieData();
        movieDataList.add(movieData);

        MovieData saveMovieData = movieDataRepository.save(movieData);

        System.out.println("saveMovieData result = " + saveMovieData.toString());

        //예약 정보 저장
        Reservation resvervation = new Reservation(movie,movieDataList);
        resvervation.addMember(member);

        reservationRepository.save(resvervation);

        em.flush();
        em.clear();

        //예약 정보 조회
        Reservation findReservation = reservationRepository.findById(resvervation.getId()).get();
        System.out.println("findReservation result = " + findReservation.toString());

        em.flush();
        em.clear();

        // 상영정보 조회 및 예약정보 추가
        MovieData findMovieData = movieDataRepository.findById(movieData.getId()).get();
        findMovieData.addReservation(findReservation);
        movieDataRepository.save(findMovieData);
        em.flush();
        em.clear();

        System.out.println("findMovieData result = " + findMovieData.toString());
*/
    }

    @Test
    @Transactional
    // 특정 회원 예약 목록 조회 테스트
    public void ReservationReadTest() {

        List<UserReservationDto> reservations = reservationRepository.searchMemberReservationList("test", "complete");

        for (UserReservationDto reservation : reservations) {
            System.out.println("read test = " + reservation.toString());
        }
    }

}