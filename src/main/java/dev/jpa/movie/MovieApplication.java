package dev.jpa.movie;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieApplication {

	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(MovieApplication.class, args);
	}

}
