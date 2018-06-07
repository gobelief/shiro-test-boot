package com.module1;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Arrays;
import java.util.Date;
import java.util.Timer;

@SpringBootApplication
@ComponentScan
@MapperScan(basePackages = {"com.module1.project.mapper"})
public class Module1Application {
    private Logger logger = LoggerFactory.getLogger(Module1Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Module1Application.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            long start = new Date().getTime();
            logger.info(ctx.getApplicationName());
            String[] beans = ctx.getBeanDefinitionNames();
            Arrays.sort(beans);
            Arrays.asList(beans).stream().forEach( a -> logger.info(a));
            logger.info("clr use time: " + (new Date().getTime() - start));
        };
    }
}
