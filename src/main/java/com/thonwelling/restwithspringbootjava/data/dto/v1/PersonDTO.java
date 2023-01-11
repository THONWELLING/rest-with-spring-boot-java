package com.thonwelling.restwithspringbootjava.data.dto.v1;


import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class PersonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;

    public PersonDTO() {}

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

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PersonDTO personDto)) return false;
    return Objects.equals(getId(),
        personDto.getId()) && Objects.equals(getFirstName(),
        personDto.getFirstName()) && Objects.equals(getLastName(),
        personDto.getLastName()) && Objects.equals(getGender(),
        personDto.getGender()) && Objects.equals(getAddress(),
        personDto.getAddress());
  }

  @Override
    public int hashCode() {
      return Objects.hash(id, getFirstName(), getLastName(), getGender(), getAddress());
    }
}
