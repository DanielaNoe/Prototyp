import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class MessageService implements Serializable {

    private List<Message> messages = new ArrayList<>();

    public MessageService() {
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void removeMessage(Message message) {
        this.messages.removeIf(m -> m.getMessage().equals(message.getMessage()) && m.getType().equals(message.getType()));
    }
}
