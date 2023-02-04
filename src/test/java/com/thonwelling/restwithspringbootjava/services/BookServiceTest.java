package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.data.dto.v1.BookDTO;
import com.thonwelling.restwithspringbootjava.models.Book;
import com.thonwelling.restwithspringbootjava.repositories.BookRepository;
import com.thonwelling.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  MockBook input;

  @InjectMocks
  private BookService bookService;
  @Mock
  BookRepository bookRepository;

  @BeforeEach
  void setUpMocks() throws Exception {
    input = new MockBook();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getBookList() {
    List<Book> list = input.mockEntityList();

    when(bookRepository.findAll()).thenReturn(list);

    var books = bookService.getBooksList();
    assertNotNull(books);
    assertEquals(14, books.size());

    var bookOne = books.get(0);
    assertNotNull(bookOne);
    assertNotNull(bookOne.getKey());
    assertNotNull(bookOne.getLinks());
    System.out.println(bookOne.toString());

    assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
    assertEquals("Some Author1", bookOne.getAuthor());
    assertNotNull(bookOne.getLaunchDate());
    assertEquals(25D, bookOne.getPrice());
    assertEquals("Some Title1", bookOne.getTitle());

    var bookSeven = books.get(6);
    assertNotNull(bookSeven);
    assertNotNull(bookSeven.getKey());
    assertNotNull(bookSeven.getLinks());
//    System.out.println(result.toString());
    assertTrue(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
    assertEquals("Some Author7", bookSeven.getAuthor());
    assertNotNull(bookSeven.getLaunchDate());
    assertEquals(25D, bookSeven.getPrice());
    assertEquals("Some Title7", bookSeven.getTitle());

    var bookTwelve = books.get(11);
    assertNotNull(bookTwelve);
    assertNotNull(bookTwelve.getKey());
    assertNotNull(bookTwelve.getLinks());
//    System.out.println(result.toString());
    assertTrue(bookTwelve.toString().contains("links: [</api/book/v1/12>;rel=\"self\"]"));
    assertEquals("Some Author12", bookTwelve.getAuthor());
    assertNotNull(bookTwelve.getLaunchDate());
    assertEquals(25D, bookTwelve.getPrice());
    assertEquals("Some Title12", bookTwelve.getTitle());
  }

  @Test
  void getBookById() throws Exception {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));

    var result = bookService.getBookById(1L);
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());
//    System.out.println(result.toString());
    assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
    assertEquals("Some Author1", result.getAuthor());
    assertEquals("Some Title1", result.getTitle());
    assertEquals(25D, result.getPrice());
    assertNotNull(result.getLaunchDate());
  }

  @Test
void createBook() throws Exception {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    BookDTO dto = input.mockDTO(1);
    dto.setKey(1L);

    when(bookRepository.save(entity)).thenReturn(entity);

    var result = bookService.createBook(dto);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
    assertEquals("Some Author1", result.getAuthor());
    assertNotNull(result.getLaunchDate());
    assertEquals(25D, result.getPrice());
    assertEquals("Some Title1", result.getTitle());
  }

  @Test
  void updateBook() throws Exception {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    BookDTO dto = input.mockDTO(1);
    dto.setKey(1L);

    when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));
    when(bookRepository.save(entity)).thenReturn(entity);

    var result = bookService.updateBook(dto);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
    assertEquals("Some Author1", result.getAuthor());
    assertNotNull(result.getLaunchDate());
    assertEquals(25D, result.getPrice());
    assertEquals("Some Title1", result.getTitle());
  }

  @Test
  void deleteBookById() {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    when(bookRepository.findById(1L)).thenReturn(Optional.of(entity));

    bookService.deleteBookById(1L);
  }
}