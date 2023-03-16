package com.thonwelling.restwithspringbootjava.controllers;

import com.thonwelling.restwithspringbootjava.data.dto.v1.security.AccountCreadentialsDTO;
import com.thonwelling.restwithspringbootjava.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthService authService;

@SuppressWarnings("rawtypes")
@Operation(summary = "Authenticates A User And Returns A Token")
@PostMapping(value = "/signin")
  public ResponseEntity signin(@RequestBody AccountCreadentialsDTO data) {
    if (checkIfParamsIsNotNull(data))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request");
    var token  = authService.signin(data);
    if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request");
    return token;
  }

private static boolean checkIfParamsIsNotNull(AccountCreadentialsDTO data) {
    return data == null || data.getUsername() == null || data.getUsername().isBlank()
        || data.getPassword() == null ||
        data.getPassword().isBlank();
  }
}
