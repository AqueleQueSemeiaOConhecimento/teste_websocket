package chat.webSocket.demo.repository;

import chat.webSocket.demo.model.ChatMessage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>
{
    List<ChatMessage> findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimestamp(
        Long senderId1, Long receiverId1, Long senderId2, Long receiverId2);
}
