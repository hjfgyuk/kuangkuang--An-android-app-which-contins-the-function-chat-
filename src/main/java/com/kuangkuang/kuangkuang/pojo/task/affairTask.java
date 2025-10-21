package com.kuangkuang.kuangkuang.pojo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class affairTask {
    @Scheduled(cron = "0 * * * * ?")
    public void deleteAffair() {
      log.info("delete affairs");
      //TODO 删除事务
    }
}
