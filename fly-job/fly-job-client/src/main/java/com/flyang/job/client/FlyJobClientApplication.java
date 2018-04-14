package com.flyang.job.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 定时任务客户端启动类
 *
 * @author meng
 * @date 2018-04-08
 */
@SpringBootApplication
@MapperScan("com.flyang.job.client.mapper")
public class FlyJobClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyJobClientApplication.class, args);
	}
}
