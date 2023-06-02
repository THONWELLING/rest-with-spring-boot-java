package com.thonwelling.restwithspringbootjava.integrationtests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.PersonDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PersonEmbeddedDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("personDTOList")
  private List<PersonDTO> persons;

  public PersonEmbeddedDTO() {}

  public List<PersonDTO> getPersons() {
    return persons;
  }

  public void setPersons(List<PersonDTO> persons) {
    this.persons = persons;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PersonEmbeddedDTO that)) return false;
    return Objects.equals(getPersons(), that.getPersons());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPersons());
  }
}