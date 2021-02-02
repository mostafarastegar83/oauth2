package com.mostafa.oauth2.authenticationserver.service;

import com.mostafa.oauth2.authenticationserver.configurations.JwtHelper;
import com.mostafa.oauth2.domain.LoginResult;
import com.mostafa.oauth2.domain.User;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class UserService {

  private static final String USERNAME = "username";
  private final JwtHelper jwtHelper;
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  public LoginResult login(String username, String password) {

    UserDetails userDetails;
    try {
      userDetails = userDetailsService.loadUserByUsername(username);
    } catch (UsernameNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
    }

    if (passwordEncoder.matches(password, userDetails.getPassword())) {
      Map<String, String> claims = new HashMap<>();
      claims.put(USERNAME, username);

      String authorities = userDetails.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.joining(","));
      claims.put("authorities", authorities);
      claims.put("userId", String.valueOf(username.hashCode()));

      String jwt = jwtHelper.createJwtForClaims(username, claims);
      return new LoginResult(jwt);
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
  }

  public User getUserDetails(JwtAuthenticationToken token) {
    Map<String, Object> attributes = token.getTokenAttributes();
    UserDetails userDetails = userDetailsService.loadUserByUsername(attributes.get(USERNAME).toString());
    return new User(userDetails.getUsername(),
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
  }
}
