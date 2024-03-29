package com.thonwelling.restwithspringbootjava.integrationtests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class WrapperPersonDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("_embedded")
  private PersonEmbeddedDTO embedded;

  public WrapperPersonDTO() {}

  public PersonEmbeddedDTO getEmbedded() {
    return embedded;
  }

  public void setEmbedded(PersonEmbeddedDTO embedded) {
    this.embedded = embedded;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof WrapperPersonDTO that)) return false;
    return Objects.equals(getEmbedded(), that.getEmbedded());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmbedded());
  }
}