package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.data.dto.v1.security.AccountCredentialsDTO;
import com.thonwelling.restwithspringbootjava.data.dto.v1.security.TokenDTO;
import com.thonwelling.restwithspringbootjava.repositories.UserRepository;
import com.thonwelling.restwithspringbootjava.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  JwtTokenProvider tokenProvider;
  @Autowired
  UserRepository repository;

  @SuppressWarnings("rawtypes")
  public ResponseEntity signin(AccountCredentialsDTO data) {
    try {
      var username = data.getUsername();
      var password = data.getPassword();
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));

      var user = repository.findByUsername(username);

      var tokenResponse = new TokenDTO();
      if (user != null) {
        tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
      } else {
        throw new UsernameNotFoundException("Username " + username + " not found!");
      }
      return ResponseEntity.ok(tokenResponse);
    } catch (Exception e) {
      throw new BadCredentialsException("Invalid username/password supplied!");
    }
  }

  @SuppressWarnings("rawtypes")
  public ResponseEntity refreshToken(String username, String refreshToken) {
    var user = repository.findByUsername(username);

    var tokenResponse = new TokenDTO();
    if (user != null) {
      tokenResponse = tokenProvider.refreshToken(refreshToken);
    } else {
      throw new UsernameNotFoundException("Username " + username + " not found!");
    }
    return ResponseEntity.ok(tokenResponse);
  }
}