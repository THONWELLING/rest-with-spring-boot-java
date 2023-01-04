package com.thonwelling.restwithspringbootjava.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private String firstName;
  private String lastName;
  private String gender;
  private String address;

  public Person(Long id, String firstName, String lastName, String gender, String address) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.address = address;
  }

  public Person() {}

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
    if (!(o instanceof Person person)) return false;
    return Objects.equals(id, person.id) && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName()) && Objects.equals(getGender(), person.getGender()) && Objects.equals(getAddress(), person.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, getFirstName(), getLastName(), getGender(), getAddress());
  }
}
