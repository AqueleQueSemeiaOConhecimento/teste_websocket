package chat.webSocket.demo.service;

import java.util.List;

import chat.webSocket.demo.model.ChatMessage;

public interface ChatService {
    ChatMessage saveMessage(ChatMessage message);
    List<ChatMessage> getAllMessages();
    List<ChatMessage> getChatBetweenUsers(Long userId1, Long userId2);
}