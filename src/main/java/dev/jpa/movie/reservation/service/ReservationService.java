package dev.jpa.movie.reservation.service;

import dev.jpa.movie.movieInfo.domain.dto.ConcurrencyMovieDto;
import dev.jpa.movie.movieInfo.domain.embedded.ConcurrencyEmId;
import dev.jpa.movie.movieInfo.domain.entity.ConcurrencyMovie;
import dev.jpa.movie.movieInfo.domain.entity.Movie;
import dev.jpa.movie.movieInfo.repository.ConcurrencyMovieRepository;
import dev.jpa.movie.movieInfo.repository.MovieRepository;
import dev.jpa.movie.reservation.domain.dto.MovieDataAccountDto;
import dev.jpa.movie.reservation.domain.dto.MovieDataDto;
import dev.jpa.movie.reservation.domain.dto.UserReservationDto;
import dev.jpa.movie.reservation.domain.entity.MovieData;
import dev.jpa.movie.reservation.domain.entity.MovieScreening;
import dev.jpa.movie.reservation.domain.entity.ReservastionAccount;
import dev.jpa.movie.reservation.domain.entity.Reservation;
import dev.jpa.movie.reservation.domain.type.SeatState;
import dev.jpa.movie.reservation.repository.MovieDataRepository;
import dev.jpa.movie.reservation.repository.ReservastionAccountRepository;
import dev.jpa.movie.reservation.repository.ReservationRepository;
import dev.jpa.movie.user.domain.entity.Member;
import dev.jpa.movie.user.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class  ReservationService {


    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final MovieDataRepository movieDataRepository;
    private final ReservastionAccountRepository reservastionAccountRepository;
    private final MovieRepository movieRepository;
    private final ConcurrencyMovieRepository concurrencyMovieRepository;
    private static final String HOST = "https://kapi.kakao.com";

    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<UserReservationDto> findUserReservationList() {

        List<UserReservationDto> result = reservationRepository.findMemberReservationList("test");

        return result;
    }
    //카카오 페이 결제 취소
    @Transactional
    public void kakaoPayCancel(Map<String, Object> result) {
        ConcurrencyMovieDto concurrencyMovieDto = (ConcurrencyMovieDto) result.get("concurrencyMovieDto");
        Long reservationId = (Long) result.get("saveReservationId");

        //동시성 제어 테이블 좌석 수정
        ConcurrencyMovie findConcurrencyMovie = concurrencyMovieRepository.findById(concurrencyMovieDto.getConcurrencyEmId()).get();

        findConcurrencyMovie.setSeatState(String.valueOf(SeatState.EMPTY));

        //예약 정보 수정
        Reservation findReservation = reservationRepository.findById(reservationId).get();

        findReservation.updateDelYn("Y");

    }
    // 영화 예약
    @Transactional
    public Map<String, Object> saveResvervation(Map<String, String> reservationInfo) throws StaleObjectStateException {

        String movieCd = reservationInfo.get("movieCd");
        String time = reservationInfo.get("time");
        String seat = reservationInfo.get("seat");

        //동시성 제어 객체 아이디 생성
        ConcurrencyEmId concurrencyEmId = buildConcurrencyEmId(movieCd,seat,time);

        //동시성 체크
        ConcurrencyMovie concurrencyMovie = concurrencyCheck(concurrencyEmId);

        //임시 멤버
        Member member = memberRepository.findById("test").get();

        //영화 객체 생성
        Movie movie = movieRepository.findById(movieCd).get();

        //예약 객체 생성 및 저장
        Reservation reservation = new Reservation(movie);
        reservation.addMember(member);

        Reservation saveReservation = reservationRepository.save(reservation);

        //상영정보 객채 생성 및 저장
        MovieScreening movieScreening = new MovieScreening(time,seat);

        MovieData movieData = new MovieData(movieScreening);
        movieData.addReservation(reservation);
        MovieData saveMovieData = movieDataRepository.save(movieData);

        Map<String, Object> result = kakaoPayReady(reservationInfo);
        MovieDataAccountDto movieDataAccountDto = MovieDataAccountDto.builder()
                .id(saveMovieData.getId())
                .movieScreening(saveMovieData.getMovieScreening())
                .reservation(saveReservation)
                .build();

        result.put("MoiveDataAccountDto", movieDataAccountDto);
        result.put("concurrencyMovieDto", buildConcurrencyMovieDto(concurrencyEmId));
        result.put("saveReservationId",saveReservation.getId());

        //좌석 예약 완료
        concurrencyMovie.setSeatState(String.valueOf(SeatState.COMPLETELY));

        return result;
    }

    //ConcurrencyEmId 객체 build후 반환
    public ConcurrencyEmId buildConcurrencyEmId(String movieCd, String seat, String time) {
        ConcurrencyEmId concurrencyEmId = ConcurrencyEmId.builder()
                .movieCd(movieCd)
                .seat(seat)
                .sceeningTime(time)
                .build();

        return concurrencyEmId;
    }

    //DTO 객체 BUILDER
    public ConcurrencyMovieDto buildConcurrencyMovieDto(ConcurrencyEmId concurrencyEmId){
        ConcurrencyMovieDto concurrencyMovieDto = ConcurrencyMovieDto.builder()
                .concurrencyEmId(concurrencyEmId)
                .build();

        return concurrencyMovieDto;
    }

    //결제정보 저장
    public ReservastionAccount saveAccount(String pgToken, MovieDataAccountDto movieDataAccountDto) {

        //임시
        Reservation reservation = movieDataAccountDto.getReservation();

        ReservastionAccount reservastionAccount = new ReservastionAccount(pgToken, reservation, "5,000");

        return reservastionAccountRepository.save(reservastionAccount);

    }
    
    // 동시성 제어 체크 함수
    public ConcurrencyMovie concurrencyCheck(ConcurrencyEmId concurrencyEmId) throws StaleObjectStateException {

        System.out.println(concurrencyEmId.toString());

        ConcurrencyMovie concurrencyMovie = concurrencyMovieRepository.findById(concurrencyEmId).get();
        concurrencyMovie.setSeatState(String.valueOf(SeatState.ONGOING));
        concurrencyMovieRepository.save(concurrencyMovie);

        return concurrencyMovie;
    }

    private Map<String, Object> kakaoPayReady(Map<String, String> reservationInfo) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        //헤더 설정
        httpHeaders.add("Authorization", "KakaoAK 2ca59210bcdd9e8b2348ebaa7d53fbeb");
        httpHeaders.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

        // 서버 요청 body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "test");
        params.add("item_name",reservationInfo.get("movieNm"));
        params.add("quantity", "1");
        params.add("total_amount", "5000");
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:3000/api/rsv/kakaoPayApproval");
        params.add("cancel_url", "http://localhost:3000/api/rsv/kakaoPayCancel");
        params.add("fail_url", "http://localhost:3000/api/rsv/kakaoPayFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, httpHeaders);
        Map<String, Object> result = new HashMap<>();

        try {
            result = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, Map.class);
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

}
