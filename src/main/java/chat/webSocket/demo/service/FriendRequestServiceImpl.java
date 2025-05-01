package chat.webSocket.demo.service;

import chat.webSocket.demo.dto.FriendRequestDTO;
import chat.webSocket.demo.model.FriendRequest;
import chat.webSocket.demo.model.User;
import chat.webSocket.demo.repository.FriendRequestRepository;
import chat.webSocket.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepository reqRepo;
    
    private final UserRepository userRepo;

    public FriendRequestServiceImpl(FriendRequestRepository reqRepo, UserRepository userRepo) {
        this.reqRepo = reqRepo;
        this.userRepo = userRepo;
    }

    @Override
    public FriendRequest sendRequest(FriendRequestDTO dto) {
        User sender = userRepo.findByUsername(dto.getSenderUsername()).orElseThrow(() -> new RuntimeException("Usuário remetente não encontrado"));
        User receiver = userRepo.findByUsername(dto.getReceiverUsername()).orElseThrow(() -> new RuntimeException("Usuário receptor não encontrado"));
        FriendRequest fr = new FriendRequest();
        fr.setSender(sender);
        fr.setReceiver(receiver);
        fr.setRequestedAt(LocalDateTime.now());
        fr.setAccepted(false);
        return reqRepo.save(fr);
    }

    @Override
    public List<FriendRequest> getPendingRequests(String receiverUsername) {
        return reqRepo.findByReceiverUsernameAndAcceptedFalse(receiverUsername);
    }

    @Override
    public FriendRequest acceptRequest(Long requestId) {
        FriendRequest fr = reqRepo.findById(requestId).orElseThrow();
        fr.setAccepted(true);
        return reqRepo.save(fr);
    }
}