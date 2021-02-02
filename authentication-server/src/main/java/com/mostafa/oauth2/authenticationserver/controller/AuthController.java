package com.mostafa.oauth2.authenticationserver.controller;

import com.mostafa.oauth2.authenticationserver.service.UserService;
import com.mostafa.oauth2.domain.LoginResult;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
  private final UserService userService;

  @PostMapping(path = "login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public LoginResult login(@RequestParam String username, @RequestParam String password) {
    return userService.login(username, password);
  }
}