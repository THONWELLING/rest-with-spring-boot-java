package com.thonwelling.restwithspringbootjava.data.dto.v1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


@JsonPropertyOrder({"id", "firstName", "lastName", "gender", "enabled", "address"})
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  @Mapping("id")
  @JsonProperty("id")
  private Long key;
  private String firstName;
  private String lastName;
  private String gender;

  private Boolean enabled;
  private String address;

  public PersonDTO() {
  }

  public Long getKey() {
    return key;
  }


  public void setKey(Long key) {
    this.key = key;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PersonDTO personDTO)) return false;
    if (!super.equals(o)) return false;
    return Objects.equals(getKey(), personDTO.getKey()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getGender(), personDTO.getGender()) && Objects.equals(getEnabled(), personDTO.getEnabled()) && Objects.equals(getAddress(), personDTO.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getKey(), getFirstName(), getLastName(), getGender(), getEnabled(), getAddress());
  }
}
