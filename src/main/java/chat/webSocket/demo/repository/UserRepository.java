package chat.webSocket.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import chat.webSocket.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}