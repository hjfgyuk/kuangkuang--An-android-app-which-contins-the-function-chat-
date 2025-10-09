package com.kuangkuang.kuangkuang;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
@EnableScheduling //开启注解方式的定时任务
@EnableCaching //开启注解方式的缓存管理
@MapperScan("com.kuangkuang.kuangkuang.mapper") //扫描mybatis的mapper接口
public class KuangkuangApplication {
	public static void main(String[] args) {
		SpringApplication.run(KuangkuangApplication.class, args);
		log.info("KuangkuangApplication started successfully!");
	}

}
