package chat.webSocket.demo.repository;

import chat.webSocket.demo.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverUsernameAndAcceptedFalse(String username);
}