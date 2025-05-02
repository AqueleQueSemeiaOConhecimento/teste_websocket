package chat.webSocket.demo.service;

import java.util.Optional;

import chat.webSocket.demo.dto.UserDTO;
import chat.webSocket.demo.model.User;

public interface UserService {
    User register(UserDTO dto);
    User login(UserDTO dto);
    Optional<User> findByUsername(String username);
}