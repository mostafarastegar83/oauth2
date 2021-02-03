package com.mostafa.oauth2.domain;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
  private String username;
  private Collection<String> authorities;
}
