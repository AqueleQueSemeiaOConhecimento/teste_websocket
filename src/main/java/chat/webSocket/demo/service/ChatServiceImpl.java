package chat.webSocket.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import chat.webSocket.demo.model.ChatMessage;
import chat.webSocket.demo.repository.ChatMessageRepository;

@Service
public class ChatServiceImpl implements ChatService{
    private final ChatMessageRepository repo;

    public ChatServiceImpl(ChatMessageRepository repo)
    {
        this.repo = repo;
    }

    @Override
    public ChatMessage saveMessage(ChatMessage message)
    {
        return repo.save(message);
    }

    @Override
    public List<ChatMessage> getAllMessages()
    {
        return repo.findAll();
    }

    public List<ChatMessage> getChatBetweenUsers(Long userId1, Long userId2)
    {
        return repo.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimestamp(
            userId1, userId2, userId2, userId1);
    }
}
