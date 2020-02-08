package org.chobit.calf.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ScheduleConfig {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);


    @Scheduled(cron = "0 0 1 * * ?")
    public void clean() {
        logger.info("------------>> executing data clean job");
    }
}