package com.thonwelling.restwithspringbootjava.data.dto.v1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


@JsonPropertyOrder({"id", "firstName", "lastName", "address", "gender", "enabled" })
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long id;
  private String firstName;
  private String lastName;
  private String gender;

  private Boolean enabled;
  private String address;

  public PersonDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getGender(), personDTO.getGender()) && Objects.equals(getEnabled(), personDTO.getEnabled()) && Objects.equals(getAddress(), personDTO.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getId(), getFirstName(), getLastName(), getGender(), getEnabled(), getAddress());
  }
}
