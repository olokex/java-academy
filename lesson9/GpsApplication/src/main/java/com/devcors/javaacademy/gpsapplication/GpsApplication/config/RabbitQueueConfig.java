package com.devcors.javaacademy.gpsapplication.GpsApplication.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {
    @Bean
    public Queue queue() {
        return new Queue("GpsLocationQueue");
    }
}
