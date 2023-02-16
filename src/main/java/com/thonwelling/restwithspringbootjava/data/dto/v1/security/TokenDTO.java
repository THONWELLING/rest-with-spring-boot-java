package com.thonwelling.restwithspringbootjava.data.dto.v1.security;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private String username;
  private Boolean authenticated;
  private Date expiration;
  private Date accessTolken;
  private Date refreshToken;

  public TokenDTO(String username,
                  Boolean authenticated,
                  Date expiration,
                  Date accessTolken,
                  Date refreshToken
  ) {
    this.username = username;
    this.authenticated = authenticated;
    this.expiration = expiration;
    this.accessTolken = accessTolken;
    this.refreshToken = refreshToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Boolean getAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(Boolean authenticated) {
    this.authenticated = authenticated;
  }

  public Date getExpiration() {
    return expiration;
  }

  public void setExpiration(Date expiration) {
    this.expiration = expiration;
  }

  public Date getAccessTolken() {
    return accessTolken;
  }

  public void setAccessTolken(Date accessTolken) {
    this.accessTolken = accessTolken;
  }

  public Date getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(Date refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TokenDTO tokenDTO)) return false;
    return Objects.equals(getUsername(), tokenDTO.getUsername()) && Objects.equals(getAuthenticated(), tokenDTO.getAuthenticated()) && Objects.equals(getExpiration(), tokenDTO.getExpiration()) && Objects.equals(getAccessTolken(), tokenDTO.getAccessTolken()) && Objects.equals(getRefreshToken(), tokenDTO.getRefreshToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUsername(), getAuthenticated(), getExpiration(), getAccessTolken(), getRefreshToken());
  }
}
