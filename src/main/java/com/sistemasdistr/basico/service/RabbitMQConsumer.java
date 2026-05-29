package com.sistemasdistr.basico.service;

import com.sistemasdistr.basico.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void recibirMensaje(String mensaje) {
        System.out.println("Mensaje recibido desde RabbitMQ: " + mensaje);
    }
}