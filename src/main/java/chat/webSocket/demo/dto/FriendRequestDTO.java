package chat.webSocket.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestDTO {
    @NotBlank
    private String senderUsername;

    @NotBlank
    private String receiverUsername;
}
