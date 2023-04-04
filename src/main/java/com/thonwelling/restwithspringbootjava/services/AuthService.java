package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.data.dto.v1.security.AccountCreadentialsDTO;
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
  private JwtTokenProvider tokenProvider;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  UserRepository repository;

  @SuppressWarnings("rawtypes")
  public ResponseEntity signin(AccountCreadentialsDTO data) {
    try {
      var userName = data.getUserName();
      var password = data.getPassword();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
      var user = repository.findByUserName(userName);
      var tokenResponse = new TokenDTO();
      if (user != null) {
        tokenResponse = tokenProvider.createAccessToken(userName, user.getRoles());
      } else {
        throw new UsernameNotFoundException("Username " + userName + " Not Found");
      }
      return ResponseEntity.ok(tokenResponse);
    } catch (Exception e) {
      throw new BadCredentialsException("Invalid Username/Password Supplied!!");
    }
  }

  @SuppressWarnings("rawtypes")
  public ResponseEntity refreshToken(String userName, String refreshToken) {
    var user = repository.findByUserName(userName);

    var tokenResponse = new TokenDTO();
    if (user != null) {
      tokenResponse = tokenProvider.refreshToken(refreshToken);
    } else {
      throw new UsernameNotFoundException("Username " + userName + " not found!");
    }
    return ResponseEntity.ok(tokenResponse);
  }
}
