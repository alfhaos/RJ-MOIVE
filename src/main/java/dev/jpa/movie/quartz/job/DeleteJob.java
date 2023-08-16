package dev.jpa.movie.quartz.job;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeleteJob {

    @GetMapping("/delete/job")
    public void delJob() throws SchedulerException {
        // 스케줄러 팩토리 생성
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        // 스케줄러 인스턴스 생성
        Scheduler scheduler = schedulerFactory.getScheduler();

        // 잡을 일시 중지시킬 JobKey 생성
        JobKey jobKey = new JobKey("movieDataInsert");

        // 잡 일시 중지
        scheduler.deleteJob(jobKey);
    }
}
