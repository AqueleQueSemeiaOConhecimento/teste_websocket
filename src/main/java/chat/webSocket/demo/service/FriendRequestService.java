package chat.webSocket.demo.service;

import chat.webSocket.demo.dto.FriendRequestDTO;
import chat.webSocket.demo.model.FriendRequest;
import java.util.List;

public interface FriendRequestService {
    FriendRequest sendRequest(FriendRequestDTO dto);
    List<FriendRequest> getPendingRequests(String receiverUsername);
    FriendRequest acceptRequest(Long requestId);
}