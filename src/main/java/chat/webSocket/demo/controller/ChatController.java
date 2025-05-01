package chat.webSocket.demo.controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.webSocket.demo.dto.ChatMessageDTO;
import chat.webSocket.demo.model.ChatMessage;
import chat.webSocket.demo.model.User;
import chat.webSocket.demo.service.ChatService;
import chat.webSocket.demo.service.UserService;

@Controller
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService)
    {
        this.chatService = chatService;
        this.userService = userService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    @ResponseBody
    public ChatMessage sendMessage(ChatMessageDTO dto)
    {
        User sender = userService.findByUsername(dto.getSenderUsername()).orElseThrow();
        ChatMessage msg = new ChatMessage();
        msg.setSender(sender);
        msg.setContent(dto.getContent());
        msg.setTimestamp(LocalDateTime.now());
        return chatService.saveMessage(msg);
    }
}
