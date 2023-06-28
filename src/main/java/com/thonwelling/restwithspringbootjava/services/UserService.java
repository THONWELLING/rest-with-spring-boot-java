package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {
  private final Logger logger = Logger.getLogger(UserService.class.getName());
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("Finding one user by name " + username + "!");
    var user = userRepository.findByUsername(username);
    if (user != null) {
      return user;
    } else {
      throw new UsernameNotFoundException("Username " + username + " not found!");
    }
  }
}