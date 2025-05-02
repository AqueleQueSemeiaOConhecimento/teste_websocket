package chat.webSocket.demo.service;

import chat.webSocket.demo.dto.FriendRequestDTO;
import chat.webSocket.demo.model.FriendRequest;
import chat.webSocket.demo.model.User;

import java.util.List;

public interface FriendRequestService {
    FriendRequest sendRequest(FriendRequestDTO dto);
    List<FriendRequest> getPendingRequests(String receiverUsername);
    List<User> getAllFriends(String username);
    FriendRequest acceptRequest(Long requestId);
}