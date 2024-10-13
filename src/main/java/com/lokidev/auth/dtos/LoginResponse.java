package com.lokidev.auth.dtos;

import lombok.Data;

@Data
public class LoginResponse {
  public String tokenType = "BEARER";
  public String token;

  public LoginResponse(String token) {
    this.token = token;
  }
}
