package dev.jpa.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.jpa.movie.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootTest
@EnableAsync
class MovieApplicationTests {

	@Autowired
	private ReservationService reservationService;
	@Test
	void contextLoads() throws JsonProcessingException {


	}

}
