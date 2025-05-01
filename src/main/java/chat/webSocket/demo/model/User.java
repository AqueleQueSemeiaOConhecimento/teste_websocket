package chat.webSocket.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @OneToMany(mappedBy = "sender")
    private Set<ChatMessage> mensagensEnviadas;

    @OneToMany(mappedBy = "sender")
    private Set<FriendRequest> sentRequests;

    @OneToMany(mappedBy = "receiver")
    private Set<FriendRequest> receivedRequests;
}
