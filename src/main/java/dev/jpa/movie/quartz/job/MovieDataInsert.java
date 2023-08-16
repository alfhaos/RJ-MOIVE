package dev.jpa.movie.quartz.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.jpa.movie.common.util.ApiUtils;
import dev.jpa.movie.movieInfo.domain.embedded.ConcurrencyEmId;
import dev.jpa.movie.movieInfo.domain.entity.ConcurrencyMovie;
import dev.jpa.movie.movieInfo.repository.ConcurrencyMovieRepository;
import dev.jpa.movie.quartz.repository.QuartzRepository;
import dev.jpa.movie.movieInfo.domain.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieDataInsert implements Job {

    private final ApiUtils apiUtils;

    private final QuartzRepository quartzRepository;

    private final ConcurrencyMovieRepository concurrencyMovieRepository;

    @Override
    @Transactional
    public void execute(JobExecutionContext context){

        String[] ssceeningTime = new String[]{"09:30", "11:30", "13:30", "15:30", "17:30"};
        String[] seat = new String[]{"A", "B", "C", "D", "E"};

        List<ConcurrencyMovie> concurrencyMovieList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // 오늘날짜로부터 -1
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String param = simpleDateFormat.format(calendar.getTime());


        try {
            List<Movie> movies = apiUtils.getMovieData(param);
            List<Movie> saveMovies = quartzRepository.saveAll(movies);

            for (Movie movie : saveMovies) {
                for (int i = 0; i < ssceeningTime.length ; i++) {
                    for (int k = 0; k < seat.length ; k++) {
                        for(int j = 1; j < 11; j++) {
                            ConcurrencyEmId concurrencyEmId = new ConcurrencyEmId(ssceeningTime[i],seat[k]+j, movie.getMovieCd());
                            ConcurrencyMovie concurrencyMovie = new ConcurrencyMovie(concurrencyEmId);
                            concurrencyMovie.addMovie(movie);
                            concurrencyMovieList.add(concurrencyMovie);
                        }

                    }
                }
            }
            concurrencyMovieRepository.saveAll(concurrencyMovieList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
