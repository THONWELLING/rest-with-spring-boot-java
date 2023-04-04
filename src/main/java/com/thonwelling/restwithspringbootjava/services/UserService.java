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
  @Autowired
  UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

   @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    logger.info("Finding A User By Name " + userName + " !!!");
    var user = repository.findByUserName(userName);
     if (user != null) {
        return user;
     } else {
       throw new UsernameNotFoundException("Username " + userName + " Not Found");
     }
  }
}