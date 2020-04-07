package org.chobit.calf.spring;


import org.chobit.calf.service.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ScheduleConfig {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Autowired
    private WorkService workService;

    @Scheduled(cron = "0 0 0-6 * * ?")
    public void updateSn() {
        logger.info("------------>> update work sn");
        workService.updateSn();
    }
}