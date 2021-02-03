package com.mostafa.oauth2.messagesservice.service;

import com.mostafa.oauth2.domain.Message;
import com.mostafa.oauth2.messagesservice.repository.MessageRepository;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {
  private final MessageRepository messageRepository;

  @PreAuthorize("hasAuthority('USER')")
  public List<Message> getCurrentUserMessages(Principal principal) {
    return messageRepository.getMessagesByUsername(principal.getName());
  }
}
