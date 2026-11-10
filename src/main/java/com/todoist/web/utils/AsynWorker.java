package com.todoist.web.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class AsynWorker {

    @Bean
    public Executor  getExecutor(){
        ThreadPoolExecutor threadPoolExecutor=
                new ThreadPoolExecutor(
                        2,
                        5,
                        3,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>()
                );
        return threadPoolExecutor;
    }
}
