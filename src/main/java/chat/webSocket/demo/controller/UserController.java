package chat.webSocket.demo.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import chat.webSocket.demo.dto.UserDTO;
import chat.webSocket.demo.model.User;
import chat.webSocket.demo.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") 
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDTO dto) {
        Optional<User> userOpt = userService.findByUsername(dto.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(dto.getPassword())) {
                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.status(401).build(); 
    }
}
