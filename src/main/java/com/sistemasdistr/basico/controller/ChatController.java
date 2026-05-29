package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage enviarMensaje(ChatMessage message) {
        return message;
    }
}