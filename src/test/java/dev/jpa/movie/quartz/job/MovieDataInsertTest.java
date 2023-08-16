package dev.jpa.movie.quartz.job;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieDataInsertTest {

    @Test
    public void test() {
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String param = simpleDateFormat.format(c1.getTime());
        System.out.println(param);
    }

}