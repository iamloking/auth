package com.lokidev.auth.controller;

import com.lokidev.auth.dtos.LoginRequest;
import com.lokidev.auth.dtos.LoginResponse;
import com.lokidev.auth.dtos.RegisterRequest;
import com.lokidev.auth.service.AuthService;
import javax.management.relation.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequest request)
      throws RoleNotFoundException {
    String response = authService.register(request);
    return new ResponseEntity<String>(response, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)
      throws RoleNotFoundException {
    String token = authService.login(request);
    return new ResponseEntity<LoginResponse>(new LoginResponse(token), HttpStatus.OK);
  }
}
