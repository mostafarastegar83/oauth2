package com.mostafa.oauth2.domain;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {
  @NonNull
  private Long id;
  @NonNull
  private OffsetDateTime createDate;
  @NonNull
  private String from;
  @NonNull
  private String message;
}
