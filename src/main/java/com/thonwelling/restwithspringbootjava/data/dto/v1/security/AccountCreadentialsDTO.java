package com.thonwelling.restwithspringbootjava.data.dto.v1.security;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class AccountCreadentialsDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private String username;
  private String password;

  public AccountCreadentialsDTO(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccountCreadentialsDTO that)) return false;
    return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUsername(), getPassword());
  }
}
