import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.UUID;

@Named
@ApplicationScoped
public class Message {
    private UUID uuid;
    private String message;
    private MessageType type;

    public Message() {
    }

    public Message(String message, MessageType type) {
        this.uuid = UUID.randomUUID();
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }
}
