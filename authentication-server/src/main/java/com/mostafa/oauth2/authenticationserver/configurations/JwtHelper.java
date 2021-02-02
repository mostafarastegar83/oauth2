package com.mostafa.oauth2.authenticationserver.configurations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class JwtHelper {

  private final RSAPrivateKey privateKey;
  private final RSAPublicKey publicKey;

  public String createJwtForClaims(String subject, Map<String, String> claims) {
    JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);

    // Add claims
    claims.forEach(jwtBuilder::withClaim);

    // Add expiredAt and etc
    return jwtBuilder
        .withNotBefore(new Date())
        .withExpiresAt(Date.from(OffsetDateTime.now().plusMinutes(2).toInstant()))
        .sign(Algorithm.RSA256(publicKey, privateKey));
  }
}