package com.thonwelling.restwithspringbootjava.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column( nullable = false, length = 120)
  private String author;
  @Column(name = "lauch_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date lauch_date;
  @Column(nullable = false)
  private Double price;
  @Column(nullable = false, length = 250)
  private String title;

  public Book() {}

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

  public Date getLauch_date() {
    return lauch_date;
  }

  public void setLauch_date(Date lauch_date) {
    this.lauch_date = lauch_date;
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
    if (!(o instanceof Book book)) return false;
    return Objects.equals(getId(), book.getId()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getLauch_date(), book.getLauch_date()) && Objects.equals(getPrice(), book.getPrice()) && Objects.equals(getTitle(), book.getTitle());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getAuthor(), getLauch_date(), getPrice(), getTitle());
  }
}
