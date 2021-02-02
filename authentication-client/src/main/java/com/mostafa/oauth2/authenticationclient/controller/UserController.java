package com.mostafa.oauth2.authenticationclient.controller;

import com.mostafa.oauth2.domain.LoginResult;
import com.mostafa.oauth2.domain.User;
import java.util.Collections;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@RestController
public class UserController {
  private RestTemplate restTemplate;

  @GetMapping("/users/current")
  public User getCurrentUser() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("username", "user");
    map.add("password", "123456");

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(map, headers);

    ResponseEntity<LoginResult>
        response = restTemplate.postForEntity("http://AUTHENTICATION-SERVER/login", request, LoginResult.class);
    if (HttpStatus.OK.equals(response.getStatusCode())) {
      LoginResult loginResult = response.getBody();
      return getUserDetails(Objects.requireNonNull(loginResult));
    }
    throw new IllegalStateException("login error");
  }

  private User getUserDetails(LoginResult loginResult) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.set("Authorization", "Bearer " + loginResult.getJwt());
    HttpEntity<String> jwtEntity = new HttpEntity(headers);
    // Use Token to get Response
    ResponseEntity<User> responseEntity =
        restTemplate.exchange("http://AUTHENTICATION-SERVER/user", HttpMethod.GET, jwtEntity, User.class);

    if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
      return responseEntity.getBody();
    }
    throw new IllegalStateException("User details error");
  }
}
