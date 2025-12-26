package com.kuangkuang.kuangkuang.pojo.task;

import ch.qos.logback.core.util.TimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuangkuang.kuangkuang.mapper.NotifyMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class affairTask {
    final private NotifyMapper notifyMapper;
    @Scheduled(cron = "* * 0 * * ?")
    public void deleteAffair() {
      log.info("delete affairs");
      QueryWrapper queryWrapper = new QueryWrapper();
      queryWrapper.lt("deadline", LocalDateTime.now());
      notifyMapper.delete(queryWrapper);
    }
}
