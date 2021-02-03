package com.mostafa.oauth2.authenticationserver.controller;

import com.nimbusds.jose.jwk.JWKSet;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JwkController {
  private final JWKSet jwkSet;

  @GetMapping("/jwks.json")
  public Map<String, Object> keys() {
    return this.jwkSet.toJSONObject();
  }
}
