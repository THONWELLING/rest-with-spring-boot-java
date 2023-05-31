package com.thonwelling.restwithspringbootjava.integrationtests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.PersonDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PersonEmbeddedDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("personDtoList")
  private List<PersonDTO> persons;

  public PersonEmbeddedDto() {}

  public List<PersonDTO> getPersons() {
    return persons;
  }

  public void setPersons(List<PersonDTO> persons) {
    this.persons = persons;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PersonEmbeddedDto that)) return false;
    return Objects.equals(getPersons(), that.getPersons());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPersons());
  }
}