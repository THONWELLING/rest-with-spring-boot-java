package com.thonwelling.restwithspringbootjava.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "first_name", nullable = false, length = 80)
  private String firstName;
  @Column(name = "last_name", nullable = false, length = 80)
  private String lastName;
  @Column(nullable = false, length = 6)
  private String gender;
  @Column(nullable = false, length = 180)
  private String address;

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
    if (!(o instanceof Person personDto)) return false;
    return Objects.equals(id, personDto.id) && Objects.equals(getFirstName(),
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
