package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.controllers.BookController;
import com.thonwelling.restwithspringbootjava.data.dto.v1.BookDTO;
import com.thonwelling.restwithspringbootjava.exceptions.ResourceNotFoundException;
import com.thonwelling.restwithspringbootjava.mapper.DozerMapper;
import com.thonwelling.restwithspringbootjava.models.Book;
import com.thonwelling.restwithspringbootjava.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
  private final Logger logger = Logger.getLogger(BookService.class.getName());
  @Autowired
  BookRepository bookRepository;
  public List<BookDTO> getBooksList() {
    logger.info("Finding All Books !!!");
    var books =  DozerMapper.parseListObjects(bookRepository.findAll(), BookDTO.class);
    books.forEach(b -> {
      try {
        b.add(linkTo(methodOn(BookController.class).getBookById(b.getKey())).withSelfRel());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    return books;
  }

  public BookDTO getBookById(Long id) throws Exception {
    logger.info("Finding A Book !!!");

    var entity = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    var dto = DozerMapper.parseObject(entity, BookDTO.class);
    dto.add(linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel());
    return dto;
  }

  public BookDTO createBook(BookDTO book) throws Exception {
    logger.info("Creating One Book !!!");
    var entity = DozerMapper.parseObject(book, Book.class);
    var dto = DozerMapper.parseObject(bookRepository.save(entity), BookDTO.class);
    dto.add(linkTo(methodOn(BookController.class).getBookById(dto.getKey())).withSelfRel());
    return dto;

  }
  public BookDTO updateBook(BookDTO book) throws Exception {
    logger.info("Updating A Book !!!");
    var entity = bookRepository.findById(book.getKey())
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    entity.setAuthor(book.getAuthor());
    entity.setLaunchDate(book.getLaunchDate());
    entity.setPrice(book.getPrice());
    entity.setTitle(book.getTitle());

    var dto = DozerMapper.parseObject(bookRepository.save(entity), BookDTO.class);
    dto.add(linkTo(methodOn(BookController.class).getBookById(dto.getKey())).withSelfRel());
    return dto;
  }

  public void deleteBookById(Long id) {
    logger.info("Deleting One Book !!!");
    var entity = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    bookRepository.delete(entity);
  }
}