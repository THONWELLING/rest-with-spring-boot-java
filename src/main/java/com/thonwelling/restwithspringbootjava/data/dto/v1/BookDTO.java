package com.thonwelling.restwithspringbootjava.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id", "author", "lauchDate", "price", "title"})
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  @Mapping("id")
  @JsonProperty("id")
  private Long key;
   private String author;
   private Date lauchDate;
    private Double price;
  private String title;

  public BookDTO() {}

  public Long getKey() {
    return key;
  }

  public void setKey(Long key) {
    this.key = key;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Date getLauchDate() {
    return lauchDate;
  }

  public void setLauchDate(Date lauchDate) {
    this.lauchDate = lauchDate;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BookDTO bookDTO)) return false;
    if (!super.equals(o)) return false;
    return Objects.equals(getKey(), bookDTO.getKey()) && Objects.equals(getAuthor(), bookDTO.getAuthor()) && Objects.equals(getLauchDate(), bookDTO.getLauchDate()) && Objects.equals(getPrice(), bookDTO.getPrice()) && Objects.equals(getTitle(), bookDTO.getTitle());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getKey(), getAuthor(), getLauchDate(), getPrice(), getTitle());
  }
}
