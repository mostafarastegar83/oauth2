package com.mostafa.oauth2.messagesservice.repository;

import com.mostafa.oauth2.domain.Message;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {
  private Map<String, List<Message>> messages;

  public MessageRepository() {
    messages = new HashMap<>();
    ArrayList<Message> messages = new ArrayList<>();
    this.messages.put("user", messages);
    messages.add(new Message(1L, OffsetDateTime.now(), "user1", "Hi"));
    messages.add(new Message(2L, OffsetDateTime.now(), "user1", "How are you doing?"));
  }

  public List<Message> getMessagesByUsername(String username) {
    return messages.get(username);
  }
}
