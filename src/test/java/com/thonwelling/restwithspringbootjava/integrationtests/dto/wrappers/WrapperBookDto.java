package com.thonwelling.restwithspringbootjava.integrationtests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class WrapperBookDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("_embedded")
  private BookEmbeddedDto embedded;

  public WrapperBookDto() {}

  public BookEmbeddedDto getEmbedded() {
    return embedded;
  }

  public void setEmbedded(BookEmbeddedDto embedded) {
    this.embedded = embedded;
  }
}