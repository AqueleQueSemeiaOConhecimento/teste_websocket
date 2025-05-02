package chat.webSocket.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private Set<ChatMessage> mensagensEnviadas;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private Set<FriendRequest> sentRequests;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    
    private Set<FriendRequest> receivedRequests;
}
