package dev.jpa.movie.reservation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.jpa.movie.common.util.ApiUtils;
import dev.jpa.movie.movieInfo.domain.embedded.ConcurrencyEmId;
import dev.jpa.movie.movieInfo.domain.entity.ConcurrencyMovie;
import dev.jpa.movie.movieInfo.domain.entity.Movie;
import dev.jpa.movie.movieInfo.repository.ConcurrencyMovieRepository;
import dev.jpa.movie.reservation.domain.dto.MovieDataAccountDto;
import dev.jpa.movie.reservation.domain.dto.UserReservationDto;
import dev.jpa.movie.reservation.domain.entity.MovieData;
import dev.jpa.movie.reservation.domain.entity.MovieScreening;
import dev.jpa.movie.reservation.domain.entity.ReservastionAccount;
import dev.jpa.movie.reservation.domain.type.SeatState;
import dev.jpa.movie.reservation.repository.MovieDataRepository;
import dev.jpa.movie.reservation.repository.ReservationRepository;
import jakarta.persistence.LockModeType;
import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MovieDataRepository movieDataRepository;

    @Autowired
    private ConcurrencyMovieRepository cm;


    @Autowired
    private ApiUtils apiUtils;


    @Test
    @Transactional
    public void concurrencyTest() throws InterruptedException {

/*
        Thread thread1 = new Thread(() -> {

            System.out.println("### Test 1 Begin ###");

            ConcurrencyEm concurrencyEm = new ConcurrencyEm("20228930", "13:30", "C5");

            ConcurrencyMovie concurrencyMovie = new ConcurrencyMovie(concurrencyEm, 0);

            ConcurrencyMovie concurrencyMovie1 = cm.findById(concurrencyMovie.getConcurrencyEm()).get();

            concurrencyMovie1.setTest(concurrencyMovie1.getTest()+1);

            cm.save(concurrencyMovie1);

            System.out.println("### Test 1 Updated : " + concurrencyMovie1.toString());
            System.out.println("### Test 1 End ###");

        });

        Thread thread2 = new Thread(() -> {
            System.out.println("### Test 2 Begin ###");

            ConcurrencyEm concurrencyEm = new ConcurrencyEm("20228930", "13:30", "B8");

            ConcurrencyMovie concurrencyMovie = new ConcurrencyMovie(concurrencyEm, 0);

            ConcurrencyMovie concurrencyMovie1 = cm.findById(concurrencyMovie.getConcurrencyEm()).get();

            concurrencyMovie1.setTest(concurrencyMovie1.getTest()+1);

            cm.save(concurrencyMovie1);

            System.out.println("### Test 2 Updated : " + concurrencyMovie1.toString());
            System.out.println("### Test 2 End ###");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
*/
    }
    // 동시성 제어 테스트
    @Test
    @Transactional
    public void concurrencyTest2() throws InterruptedException {


        Thread thread1 = new Thread(() -> {

            System.out.println("### Test 1 Begin ###");
            MovieData movieData = movieDataRepository.findDataForLock("20220859", "B5", "11:30");

            movieDataRepository.save(movieData);
            System.out.println("### Test 1 Updated : ");
            System.out.println("### Test 1 End ###");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("### Test 2 Begin ###");

            MovieData movieData = movieDataRepository.findDataForLock("20220859", "B5", "11:30");

            movieDataRepository.save(movieData);
            System.out.println("### Test 2 Updated : ");
            System.out.println("### Test 2 End ###");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

    @Test
    public void concurrencyTest3() throws InterruptedException {

        Map<String, String> reservationInfo = new HashMap<>();

        reservationInfo.put("movieCd","20204264");
        reservationInfo.put("time","09:30");
        reservationInfo.put("seat","A1");



        Thread thread1 = new Thread(() -> {
            try{
                reservationInfo.put("state","A");
                System.out.println("test1 Start ====================");
                reservationService.saveResvervation(reservationInfo);
                System.out.println("test1 END ====================");
            } catch (StaleObjectStateException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try{
                reservationInfo.put("state","A");
                System.out.println("test1 Start ====================");
                reservationService.saveResvervation(reservationInfo);
                System.out.println("test1 END ====================");
            } catch (StaleObjectStateException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

    @Test
    @Transactional
    public void test() throws InterruptedException {
        Map<String, String> reservationInfo = new HashMap<>();

        reservationInfo.put("movieCd","20204264");
        reservationInfo.put("time","09:30");
        reservationInfo.put("seat","A10");
        reservationInfo.put("movieName","콘크리트 어쩌구");

        String movieCd = reservationInfo.get("movieCd");
        String time = reservationInfo.get("time");
        String seat = reservationInfo.get("seat");

        reservationInfo.put("state","test1");
        ConcurrencyEmId concurrencyEmId = new ConcurrencyEmId(time,seat,movieCd);
        ConcurrencyMovie concurrencyMovie = cm.findById(concurrencyEmId).get();

//        Thread thread1 = new Thread(() -> {
//            try{
//
//                cm.save(concurrencyMovie);
//                System.out.println("테스트 1 = " + concurrencyMovie.getId());
//
//            } catch (StaleObjectStateException e) {
//                e.printStackTrace();
//            }
//
//        });
//
//        Thread thread2 = new Thread(() -> {
//            try{
//                reservationInfo.put("state","test2");
//                ConcurrencyEmId concurrencyEmId = new ConcurrencyEmId(time,seat,movieCd);
//                ConcurrencyMovie concurrencyMovie = cm.findById(concurrencyEmId).get();
//                concurrencyMovie.setSeatState(reservationInfo.get("state"));
//                cm.save(concurrencyMovie);
//                System.out.println("테스트 2 = " + concurrencyMovie.getId());
//
//            } catch (StaleObjectStateException e) {
//                e.printStackTrace();
//            }
//        });
//
//        thread1.start();
//        thread2.start();
//
//        thread1.join();
//        thread2.join();
    }

    @Test
    public void findUserReservationList() {
        List<UserReservationDto> list = reservationService.findUserReservationList();

        for (UserReservationDto userReservationDto : list) {
            System.out.println(userReservationDto.toString());
        }
    }
}



