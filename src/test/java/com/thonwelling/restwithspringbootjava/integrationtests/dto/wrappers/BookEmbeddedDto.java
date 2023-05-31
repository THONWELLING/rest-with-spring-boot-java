package com.thonwelling.restwithspringbootjava.integrationtests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.BookDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class BookEmbeddedDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("bookDtoList")
  private List<BookDTO> books;

  public BookEmbeddedDto() {}

  public List<BookDTO> getBooks() {
    return books;
  }

  public void setBooks(List<BookDTO> books) {
    this.books = books;

  }
}