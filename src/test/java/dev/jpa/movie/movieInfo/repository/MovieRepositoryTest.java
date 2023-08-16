package dev.jpa.movie.movieInfo.repository;

import dev.jpa.movie.movieInfo.domain.dto.SeatListDto;
import dev.jpa.movie.movieInfo.domain.entity.Movie;
import dev.jpa.movie.reservation.domain.entity.MovieData;
import dev.jpa.movie.reservation.domain.entity.MovieScreening;
import dev.jpa.movie.reservation.domain.entity.Reservation;
import dev.jpa.movie.reservation.domain.type.SeatState;
import dev.jpa.movie.reservation.repository.MovieDataRepository;
import dev.jpa.movie.reservation.repository.ReservationRepository;
import dev.jpa.movie.user.domain.entity.Member;
import dev.jpa.movie.user.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MovieDataRepository movieDataRepository;

    @PersistenceContext
    private EntityManager em;


    @Test
    @Transactional
    public void Test() {
        // 회원 저장 및 조회
        Member member = new Member("test","홍길동", "010-1234-5678", "123456");
        Member saveMember = memberRepository.save(member);

        Movie movie = movieRepository.findById("20228930").get();

        em.flush();
        em.clear();

        //예약 정보 저장
        Reservation resvervation = new Reservation(movie);
        resvervation.addMember(member);

        reservationRepository.save(resvervation);

        em.flush();
        em.clear();

        // 상영정보 저장
        List<MovieData> movieDataList = new ArrayList<>();
        MovieScreening movieScreening = new MovieScreening("11:30","C10");

        MovieData movieData = new MovieData(movieScreening);
        movieData.addReservation(resvervation);
        movieDataList.add(movieData);

        MovieData saveMovieData = movieDataRepository.save(movieData);

        em.flush();
        em.clear();


    }
    // 좌석조회 테스트
    @Test
    @Transactional
    public void test2() {
        List<SeatListDto> result = movieRepository.findSeatList("11:30", "20228930");

        for (SeatListDto seatListDto : result) {
            System.out.println("result = " + seatListDto.getSeat());
        }
    }
}