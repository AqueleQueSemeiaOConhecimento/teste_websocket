package chat.webSocket.demo.repository;

import chat.webSocket.demo.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
    
}
