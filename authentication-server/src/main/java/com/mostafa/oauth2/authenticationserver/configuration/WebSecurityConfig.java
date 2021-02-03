package com.mostafa.oauth2.authenticationserver.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final PasswordEncoder passwordEncoder;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests(configurer ->
            configurer
                .antMatchers(
                    "/error",
                    "/login", "/jwks.json"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
        )
        .exceptionHandling().disable()
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
  }

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {
    UserDetails user1 = User
        .withUsername("user")
        .authorities("USER")
        .passwordEncoder(passwordEncoder::encode)
        .password("123456")
        .build();

    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(user1);
    return manager;
  }

}