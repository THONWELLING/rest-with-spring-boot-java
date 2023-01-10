package com.thonwelling.restwithspringbootjava.data.dto.v2;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PersonVOV2 implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDay;
    private String address;

    public PersonVOV2() {}

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

  public Date getBirthDay() {
    return birthDay;
  }

  public void setBirthDay(Date birthDay) {
    this.birthDay = birthDay;
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
    if (!(o instanceof PersonVOV2 that)) return false;
    return Objects.equals(getId(),
        that.getId()) && Objects.equals(getFirstName(),
        that.getFirstName()) && Objects.equals(getLastName(),
        that.getLastName()) && Objects.equals(getGender(),
        that.getGender()) && Objects.equals(getBirthDay(),
        that.getBirthDay()) && Objects.equals(getAddress(),
        that.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getFirstName(), getLastName(), getGender(), getBirthDay(), getAddress());
  }
}
