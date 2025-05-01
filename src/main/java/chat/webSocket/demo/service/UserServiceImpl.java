package chat.webSocket.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import chat.webSocket.demo.dto.UserDTO;
import chat.webSocket.demo.model.User;
import chat.webSocket.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repo;
    public UserServiceImpl (UserRepository repo)
    {
        this.repo = repo;
    }

    @Override
    public User register(UserDTO dto) {
        Optional<User> existingUser = repo.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Nome de usuário já está em uso.");
        }
    
        // Criação do novo usuário
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return repo.save(user);
    }
    

    @Override
    public Optional<User> findByUsername(String username)
    {
        return repo.findByUsername(username);
    }
}
