package com.thonwelling.restwithspringbootjava.integrationtests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.BookDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class BookEmbeddedDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("bookDTOList")
  private List<BookDTO> books;

  public BookEmbeddedDTO() {}

  public List<BookDTO> getBooks() {
    return books;
  }

  public void setBooks(List<BookDTO> books) {
    this.books = books;

  }
}