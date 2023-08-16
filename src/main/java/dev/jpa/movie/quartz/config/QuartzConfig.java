package dev.jpa.movie.quartz.config;

import dev.jpa.movie.quartz.job.MovieDataInsert;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail dataLoadJobDetail() {
        return JobBuilder.newJob(MovieDataInsert.class)
                .withIdentity("movieDataInsert24")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger dataLoadJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(24)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(dataLoadJobDetail())
                .withIdentity("dataLoadJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
