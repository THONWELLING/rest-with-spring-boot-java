package com.thonwelling.restwithspringbootjava.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id", "author", "lauchDate", "price", "title"})
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  @JsonProperty("id")
  private Long id;
   private String author;
   private Date launchDate;
    private Double price;
  private String title;

  public BookDTO() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Date getLaunchDate() {
    return launchDate;
  }

  public void setLaunchDate(Date launchDate) {
    this.launchDate = launchDate;
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
    return Objects.equals(getId(), bookDTO.getId()) && Objects.equals(getAuthor(), bookDTO.getAuthor()) && Objects.equals(getLaunchDate(), bookDTO.getLaunchDate()) && Objects.equals(getPrice(), bookDTO.getPrice()) && Objects.equals(getTitle(), bookDTO.getTitle());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getId(), getAuthor(), getLaunchDate(), getPrice(), getTitle());
  }
}
