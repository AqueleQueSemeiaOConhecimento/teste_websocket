package chat.webSocket.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chat.webSocket.demo.dto.FriendRequestDTO;
import chat.webSocket.demo.model.FriendRequest;
import chat.webSocket.demo.model.User;
import chat.webSocket.demo.service.FriendRequestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/friends")
public class FriendRequestController {
    private final FriendRequestService frService;

    public FriendRequestController(FriendRequestService frService) { this.frService = frService; }

    @PostMapping("/request")
    public ResponseEntity<FriendRequest> sendRequest(@Valid @RequestBody FriendRequestDTO dto) {
        return ResponseEntity.ok(frService.sendRequest(dto));
    }

    @GetMapping("/pending/{username}")
    public ResponseEntity<List<FriendRequest>> getPending(@PathVariable String username) {
        return ResponseEntity.ok(frService.getPendingRequests(username));
    }

    @GetMapping("/get-all-friends/{username}")
    public List<User> getFriends(@PathVariable String username) 
    {
        return frService.getAllFriends(username);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<FriendRequest> accept(@PathVariable Long id) {
        return ResponseEntity.ok(frService.acceptRequest(id));
    }
}
