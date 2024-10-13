package com.lokidev.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dummy/")
public class DummyController {

  @GetMapping()
  public ResponseEntity<String> sayHello() {
    return new ResponseEntity<>("Hello", HttpStatus.OK);
  }
}
