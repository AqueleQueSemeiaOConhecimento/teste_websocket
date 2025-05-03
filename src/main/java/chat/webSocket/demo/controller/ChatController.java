package chat.webSocket.demo.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import chat.webSocket.demo.dto.ChatMessageDTO;
import chat.webSocket.demo.model.ChatMessage;
import chat.webSocket.demo.model.User;
import chat.webSocket.demo.service.ChatService;
import chat.webSocket.demo.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, UserService userService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/sendMessage")
    public ChatMessage sendMessage(@RequestBody ChatMessageDTO dto) {
        User sender = userService.findByUsername(dto.getSenderUsername()).orElseThrow();
        User receiver = userService.findByUsername(dto.getReceiverUsername()).orElseThrow();
        ChatMessage msg = new ChatMessage();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(dto.getContent());
        msg.setTimestamp(LocalDateTime.now());

        ChatMessage savedMessage = chatService.saveMessage(msg);

        messagingTemplate.convertAndSend("/topic/messages/" + receiver.getUsername(), savedMessage);

        return savedMessage;
    }

    @GetMapping("/chat")
    public List<ChatMessage> getChatBetweenUsers(@RequestParam Long userId1, @RequestParam Long userId2) {
        return chatService.getChatBetweenUsers(userId1, userId2);
    }
}
