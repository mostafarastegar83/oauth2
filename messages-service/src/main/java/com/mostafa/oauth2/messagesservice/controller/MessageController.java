package com.mostafa.oauth2.messagesservice.controller;

import com.mostafa.oauth2.domain.Message;
import com.mostafa.oauth2.messagesservice.service.MessageService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {
  private final MessageService messageService;

  @GetMapping
  public List<Message> getMessages(Principal principal) {
    return messageService.getCurrentUserMessages(principal);
  }
}
