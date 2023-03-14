package com.thonwelling.restwithspringbootjava.securityJwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thonwelling.restwithspringbootjava.data.dto.v1.security.TokenDTO;
import com.thonwelling.restwithspringbootjava.exceptions.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

  @Value("${security.jwt.token.secret-key:secret}")
  private String secretKey = "secret";

  @Value("${security.jwt.token.expire-lenght:3600000}")
  private long validityInMilliseconds = 3600000; //1 hora

  @Autowired
  private UserDetailsService userDetailsService;

  Algorithm algorithm = null;

  @PostConstruct
  protected void init(){
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    algorithm = Algorithm.HMAC256(secretKey.getBytes());
  }

  public TokenDTO createAccessToken(String username, List<String> roles){
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);
    var accessToken = getRefreshToken(username, roles, now, validity);
    var refreshToken = getRefreshToken(username, roles, now);
    return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
  }

  private String getRefreshToken(String username, List<String> roles, Date now, Date validity) {
    String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    return JWT.create()
        .withClaim("roles", roles)
        .withIssuedAt(now)
        .withExpiresAt(validity)
        .withSubject(username)
        .withIssuer(issuerUrl)
        .sign(algorithm)
        .strip();
  }
  private String getRefreshToken(String username, List<String> roles, Date now) {
    Date validityRefreshToken = new Date(now.getTime() + (validityInMilliseconds *3));

    return JWT.create()
        .withClaim("roles", roles)
        .withIssuedAt(now)
        .withExpiresAt(validityRefreshToken)
        .withSubject(username)
        .sign(algorithm)
        .strip();
  }

  public Authentication getAuthentication(String token) {
    DecodedJWT decodedJWT = decodedToken(token);
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private DecodedJWT decodedToken(String token) {
    Algorithm algorithm1 = Algorithm.HMAC256(secretKey.getBytes());
    JWTVerifier verifier = JWT.require(algorithm1).build();
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT;
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring("Bearer ".length());
    }
    return null;
  }

  public boolean validateToken(String token) {
    DecodedJWT decodedJWT = decodedToken(token);
    try {
      if (decodedJWT.getExpiresAt().before(new Date())) {
        return false;
      }
      return true;
    } catch(Exception e) {
      throw new InvalidJwtAuthenticationException("Expired Or Invalid Token!!");
    }
  }
}
