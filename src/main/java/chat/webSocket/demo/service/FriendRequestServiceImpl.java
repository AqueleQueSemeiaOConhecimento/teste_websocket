package chat.webSocket.demo.service;

import chat.webSocket.demo.dto.FriendRequestDTO;
import chat.webSocket.demo.model.FriendRequest;
import chat.webSocket.demo.model.User;
import chat.webSocket.demo.repository.FriendRequestRepository;
import chat.webSocket.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepository reqRepo;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final UserRepository userRepo;

    public FriendRequestServiceImpl(FriendRequestRepository reqRepo, UserRepository userRepo)
    {
        this.reqRepo = reqRepo;
        this.userRepo = userRepo;
    }

    @Override
    public FriendRequest sendRequest(FriendRequestDTO dto)
    {
        User sender = userRepo.findByUsername(dto.getSenderUsername())
            .orElseThrow(() -> new RuntimeException("Usuário remetente não encontrado"));

        User receiver = userRepo.findByUsername(dto.getReceiverUsername())
            .orElseThrow(() -> new RuntimeException("Usuário receptor não encontrado"));

        boolean alreadyFriends = reqRepo.existsByAcceptedTrueAndUsers(sender.getUsername(), receiver.getUsername());
        if (alreadyFriends) {
            throw new RuntimeException("Esses usuários já são amigos.");
        }

        boolean pending = reqRepo.existsByAcceptedFalseAndUsers(sender.getUsername(), receiver.getUsername());
        if (pending) {
            throw new RuntimeException("Já existe uma solicitação de amizade pendente.");
        }

        FriendRequest fr = new FriendRequest();
        fr.setSender(sender);
        fr.setReceiver(receiver);
        fr.setRequestedAt(LocalDateTime.now());
        fr.setAccepted(false);

        return reqRepo.save(fr);
    }


    @Override
    public List<FriendRequest> getPendingRequests(String receiverUsername)
    {
        return reqRepo.findByReceiverUsernameAndAcceptedFalse(receiverUsername);
    }

    @Override
    public List<User> getAllFriends(String username)
    {
        List<FriendRequest> friendRequests = reqRepo.findByAcceptedTrueAndSenderUsernameOrAcceptedTrueAndReceiverUsername(username, username);

        List<User> friends = friendRequests.stream()
        .map(fr -> {
            if (fr.getSender().getUsername().equals(username)) {
                return fr.getReceiver();
            } else {
                return fr.getSender();
            }
        })
        .collect(Collectors.toList());
    
        return friends;
    }

    @Override
    public FriendRequest acceptRequest(Long requestId) {
        FriendRequest fr = reqRepo.findById(requestId).orElseThrow();
        fr.setAccepted(true);
        FriendRequest saved = reqRepo.save(fr);

        String sender = saved.getSender().getUsername();
        String receiver = saved.getReceiver().getUsername();

        messagingTemplate.convertAndSend("/topic/friends/" + sender, "updated");
        messagingTemplate.convertAndSend("/topic/friends/" + receiver, "updated");

        return saved;
    }


}