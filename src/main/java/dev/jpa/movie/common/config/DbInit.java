package dev.jpa.movie.common.config;

import dev.jpa.movie.movieInfo.domain.embedded.ConcurrencyEmId;
import dev.jpa.movie.movieInfo.domain.entity.ConcurrencyMovie;
import dev.jpa.movie.movieInfo.domain.entity.Movie;
import dev.jpa.movie.movieInfo.repository.MovieRepository;
import dev.jpa.movie.reservation.domain.entity.MovieData;
import dev.jpa.movie.reservation.domain.entity.MovieScreening;
import dev.jpa.movie.reservation.domain.entity.Reservation;
import dev.jpa.movie.reservation.domain.type.SeatState;
import dev.jpa.movie.reservation.repository.MovieDataRepository;
import dev.jpa.movie.reservation.repository.ReservationRepository;
import dev.jpa.movie.user.domain.entity.Member;
import dev.jpa.movie.user.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ManyToMany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final InitService initService;

    @PostConstruct
    public void Init() {
//        initService.dbInit1();
       // initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        private final MemberRepository memberRepository;
        private final ReservationRepository reservationRepository;
        private final MovieDataRepository movieDataRepository;
        private final MovieRepository movieRepository;
        public void dbInit1() {
//
//            ConcurrencyEm concurrencyEm2 = new ConcurrencyEm("20228930", "13:30", "B8");
//
//            ConcurrencyMovie concurrencyMovie2 = new ConcurrencyMovie(concurrencyEm2, 0);
//            em.persist(concurrencyMovie2);

//            em.flush();
//            em.clear();

        }

        public void dbInit2() {

//            Member member = new Member("test","홍길동","010-1234-5678","1234");
//            Member saveMember = memberRepository.save(member);
//
//            Movie movie = new Movie("20220859");
//            Movie saveMovie = movieRepository.save(movie);
//
//            Reservation reservation = new Reservation(saveMovie);
//            reservation.addMember(saveMember);
//
//            Reservation saveReservation = reservationRepository.save(reservation);
//
//            MovieScreening movieScreening = new MovieScreening("11:30","B5");
//
//            MovieData movieData = new MovieData("ONGOING", movieScreening);
//            movieData.addReservation(saveReservation);
//
//            movieDataRepository.save(movieData);

        }
    }

}


