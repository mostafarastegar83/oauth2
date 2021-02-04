package com.mostafa.oauth2.messagesservice.configuration;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter
        .setJwtGrantedAuthoritiesConverter(jwt -> Arrays.stream(jwt.getClaimAsString("authorities").split(","))
            .map(Object::toString)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()));
    jwtAuthenticationConverter.setPrincipalClaimName("username");
    http
        .cors()
        .and()
        .csrf().disable()
        .authorizeRequests(authz -> authz
            .antMatchers(HttpMethod.GET, "/messages/**").hasAuthority("ROLE_USER")
            .anyRequest().authenticated())
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(jwtAuthenticationConverter);
  }
}