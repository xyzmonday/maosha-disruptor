package com.yff.maosha;

import com.yff.maosha.config.ItemAmountUpdateProperties;
import com.yff.maosha.config.OrderInsertProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.yff.maosha"})
@MapperScan("com.yff.maosha.mapper")
public class MaoshaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaoshaApplication.class,args);
    }
}
