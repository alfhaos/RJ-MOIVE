package dev.jpa.movie.quartz.config;

import jakarta.annotation.PostConstruct;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuartzSchedulerInitializer {

    private final Scheduler scheduler;

    public QuartzSchedulerInitializer() throws SchedulerException {
        // 스케줄러 팩토리 생성
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        // 스케줄러 인스턴스 생성
        this.scheduler = schedulerFactory.getScheduler();
    }

    @PostConstruct
    public void init() throws SchedulerException {
        // 스케줄러 초기화
        scheduler.start();
    }
}