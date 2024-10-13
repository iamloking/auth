package com.lokidev.auth.exceptions;

import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Handle Authentication Exceptions
  @ExceptionHandler(AuthenticationException.class)
  public ProblemDetail handleAuthenticationException(AuthenticationException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
    problemDetail.setTitle("Unauthorized");
    problemDetail.setDetail(ex.getMessage());
    problemDetail.setInstance(URI.create("/api/authentication"));
    problemDetail.setProperty("timestamp", LocalDateTime.now());
    return problemDetail;
  }

  // Handle Bad Credentials
  @ExceptionHandler(BadCredentialsException.class)
  public ProblemDetail handleBadCredentialsException(BadCredentialsException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
    problemDetail.setTitle("Forbidden");
    problemDetail.setDetail("Invalid username or password");
    problemDetail.setInstance(URI.create("/api/login"));
    problemDetail.setProperty("timestamp", LocalDateTime.now());
    return problemDetail;
  }

  // Handle other types of Authentication-related exceptions
  @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
  public ProblemDetail handleAuthenticationCredentialsNotFoundException(
      AuthenticationCredentialsNotFoundException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
    problemDetail.setTitle("Unauthorized");
    problemDetail.setDetail("Authentication credentials not found");
    problemDetail.setInstance(URI.create("/api/authentication"));
    problemDetail.setProperty("timestamp", LocalDateTime.now());
    return problemDetail;
  }

  // Global Exception Handler
  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGlobalException(Exception ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    problemDetail.setTitle("Internal Server Error");
    problemDetail.setDetail(ex.getMessage());
    problemDetail.setInstance(URI.create("/api/error"));
    problemDetail.setProperty("timestamp", LocalDateTime.now());
    return problemDetail;
  }
}
