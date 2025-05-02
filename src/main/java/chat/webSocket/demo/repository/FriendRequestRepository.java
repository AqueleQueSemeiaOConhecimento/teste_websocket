package chat.webSocket.demo.repository;

import chat.webSocket.demo.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverUsernameAndAcceptedFalse(String username);
    List<FriendRequest> findByAcceptedTrueAndSenderUsernameOrAcceptedTrueAndReceiverUsername(String senderUsername, String receiverUsername);

    @Query("SELECT CASE WHEN COUNT(fr) > 0 THEN true ELSE false END FROM FriendRequest fr " +
       "WHERE fr.accepted = true AND " +
       "((fr.sender.username = :user1 AND fr.receiver.username = :user2) OR " +
       "(fr.sender.username = :user2 AND fr.receiver.username = :user1))")
    boolean existsByAcceptedTrueAndUsers(@Param("user1") String user1, @Param("user2") String user2);

    @Query("SELECT CASE WHEN COUNT(fr) > 0 THEN true ELSE false END FROM FriendRequest fr " +
       "WHERE fr.accepted = false AND " +
       "((fr.sender.username = :user1 AND fr.receiver.username = :user2) OR " +
       "(fr.sender.username = :user2 AND fr.receiver.username = :user1))")
    boolean existsByAcceptedFalseAndUsers(@Param("user1") String user1, @Param("user2") String user2);

}