package dev.jpa.movie.reservation.controller;

import ch.qos.logback.core.model.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import dev.jpa.movie.movieInfo.repository.MovieRepository;
import dev.jpa.movie.reservation.domain.dto.MovieDataAccountDto;
import dev.jpa.movie.reservation.domain.dto.ReservationInfo;
import dev.jpa.movie.reservation.domain.dto.UserReservationDto;
import dev.jpa.movie.reservation.domain.entity.MovieData;
import dev.jpa.movie.reservation.domain.entity.Reservation;
import dev.jpa.movie.reservation.service.ReservationService;
import jakarta.persistence.Access;
import jakarta.persistence.LockModeType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rsv")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    @Value("${movie.url}")
    private String indexUrl;

    @GetMapping("/movie")
    public void getMovieData(Reservation reservation) throws JsonProcessingException {
        reservationService.save(reservation);
    }

    @PostMapping("/kakaoPay")
    public String kakaoPayReady(@RequestBody Map<String, String> reservationInfo, HttpSession session) throws Exception {

        Map<String, Object> result = reservationService.saveResvervation(reservationInfo);

        session.setAttribute("reservationResult", result);

        session.setMaxInactiveInterval(30*60);

        return (String) result.get("next_redirect_pc_url");
    }

    @GetMapping("/kakaoPayApproval")
    public void kakaoPayApproval(@RequestParam("pg_token") String pgToken, HttpSession session, HttpServletResponse response) throws IOException {

        Map<String, Object> result = (Map<String, Object>) session.getAttribute("reservationResult");

        MovieDataAccountDto movieDataAccountDto = (MovieDataAccountDto) result.get("MoiveDataAccountDto");

        reservationService.saveAccount(pgToken, movieDataAccountDto);

        response.sendRedirect("http://localhost:3000/kakaoPayApproval");
    }

    @GetMapping("/kakaoPayCancel")
    public void kakaoPayCancel(HttpSession session, HttpServletResponse response) throws IOException {

        Map<String, Object> result = (Map<String, Object>) session.getAttribute("reservationResult");

        reservationService.kakaoPayCancel(result);

        response.sendRedirect(indexUrl);

    }

    @GetMapping("/userReservationList")
    public List<UserReservationDto> reservationList(HttpSession session) {

        List<UserReservationDto> list = reservationService.findUserReservationList();

        return list;
    }
}
