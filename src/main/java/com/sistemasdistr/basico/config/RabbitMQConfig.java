package com.sistemasdistr.basico.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "incidenciasQueue";

    @Bean
    public Queue incidenciasQueue() {
        return new Queue(QUEUE_NAME, false);
    }
}