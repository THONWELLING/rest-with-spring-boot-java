package com.thonwelling.restwithspringbootjava.config;


import com.thonwelling.restwithspringbootjava.security.jwt.JwtConfigurer;
import com.thonwelling.restwithspringbootjava.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
  @Autowired
  JwtTokenProvider tokenProvider;
  @Bean
  PasswordEncoder passwordEncoder() {
    Map<String, PasswordEncoder> encoders = new HashMap<>();

    BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
    encoders.put("bcrypt", bCryptEncoder);
    DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
    passwordEncoder.setDefaultPasswordEncoderForMatches(bCryptEncoder);
    return passwordEncoder;
  }

  @Bean
  AuthenticationManager authenticationManagerBean(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .httpBasic().disable()
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(
          authorizeHttpRequests -> authorizeHttpRequests
          .requestMatchers(
              "/auth/signin",
              "/auth/refresh/**",
              "/swagger-ui/**",
              "/v3/api-docs/**"
          ).permitAll()
          .requestMatchers("/api/**").authenticated()
          .requestMatchers("/users").denyAll()
      )
      .cors()
      .and()
      .apply(new JwtConfigurer(tokenProvider))
      .and()
      .build();
  }
}