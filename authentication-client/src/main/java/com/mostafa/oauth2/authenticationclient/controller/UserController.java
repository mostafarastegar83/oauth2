package com.mostafa.oauth2.authenticationclient.controller;

import com.mostafa.oauth2.domain.LoginResult;
import com.mostafa.oauth2.domain.Message;
import com.mostafa.oauth2.domain.User;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private RestTemplate restTemplate;

  @GetMapping("/current")
  public User getCurrentUser() {
    LoginResult loginResult = login();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.set("Authorization", "Bearer " + loginResult.getJwt());
    HttpEntity<String> jwtEntity = new HttpEntity(headers);
    ResponseEntity<User> responseEntity =
        restTemplate.exchange("http://AUTHENTICATION-SERVER/user", HttpMethod.GET, jwtEntity, User.class);

    if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
      return responseEntity.getBody();
    }
    throw new IllegalStateException("User details error");
  }

  @GetMapping("/current/messages")
  public List<Message> getCurrentUserMessages() {
    LoginResult loginResult = login();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.set("Authorization", "Bearer " + loginResult.getJwt());
    HttpEntity<String> jwtEntity = new HttpEntity<>(headers);
    ParameterizedTypeReference<List<Message>> typeRef = new ParameterizedTypeReference<>() {
    };
    ResponseEntity<List<Message>> responseEntity =
        restTemplate.exchange("http://MESSAGES-SERVICE/messages", HttpMethod.GET, jwtEntity, typeRef);

    if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
      return responseEntity.getBody();
    }
    throw new IllegalStateException("User details error");
  }

  private LoginResult login() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("username", "user");
    map.add("password", "123456");

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(map, headers);

    ResponseEntity<LoginResult>
        response = restTemplate.postForEntity("http://AUTHENTICATION-SERVER/login", request, LoginResult.class);
    if (HttpStatus.OK.equals(response.getStatusCode())) {
      return response.getBody();
    }
    throw new IllegalStateException("login error");
  }
}
