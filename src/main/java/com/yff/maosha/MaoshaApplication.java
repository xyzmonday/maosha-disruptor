package com.yff.maosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.yff.maosha"})
@MapperScan("com.yff.maosha.mapper")
@EnableScheduling
public class MaoshaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaoshaApplication.class,args);
    }
}
