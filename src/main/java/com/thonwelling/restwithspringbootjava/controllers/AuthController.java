package com.thonwelling.restwithspringbootjava.controllers;


import com.thonwelling.restwithspringbootjava.data.dto.v1.security.AccountCredentialsDTO;
import com.thonwelling.restwithspringbootjava.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  AuthService authService;

  @SuppressWarnings("rawtypes")
  @Operation(summary = "Authenticates a user and returns a token")
  @PostMapping(value = "/signin")
  public ResponseEntity signin(@RequestBody AccountCredentialsDTO data) {
    if (checkIfParamsIsNotNull(data))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
    var token = authService.signin(data);
    if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
    return token;
  }

  @SuppressWarnings("rawtypes")
  @Operation(summary = "Refresh token for authenticated user and returns a token")
  @PutMapping(value = "/refresh/{username}")
  public ResponseEntity refreshToken(@PathVariable("username") String username,
                                     @RequestHeader("Authorization") String refreshToken) {
    if (checkIfParamsIsNotNull(username, refreshToken))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
    var token = authService.refreshToken(username, refreshToken);
    if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
    return token;
  }

  private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
    return refreshToken == null || refreshToken.isBlank() ||
        username == null || username.isBlank();
  }

  private boolean checkIfParamsIsNotNull(AccountCredentialsDTO data) {
    return data == null || data.getUsername() == null || data.getUsername().isBlank()
        || data.getPassword() == null || data.getPassword().isBlank();
  }
}