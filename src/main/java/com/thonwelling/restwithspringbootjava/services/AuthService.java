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
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  @Autowired
  private JwtTokenProvider tokenProvider;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  UserRepository repository;

  public ResponseEntity signin(AccountCreadentialsDTO data) {
    try {
      var username = data.getUsername();
      var password = data.getPassword();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      var user = repository.findByUserName(username);
      var tokenResponse = new TokenDTO();
      if (user != null) {
        tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
      } else {
        throw new BadCredentialsException("Username" + username + "Not Found");
      }

      return ResponseEntity.ok(tokenResponse);
    } catch (Exception e) {
      throw new BadCredentialsException("Invalid Uername/Password Supplied!!");
    }
  }
}
