package com.mostafa.oauth2.authenticationserver.controller;

import com.mostafa.oauth2.authenticationserver.service.UserService;
import com.mostafa.oauth2.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @GetMapping
  public User getUser(JwtAuthenticationToken token) {
    return userService.getUserDetails(token);
  }
}