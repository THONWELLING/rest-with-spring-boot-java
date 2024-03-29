package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.controllers.BookController;
import com.thonwelling.restwithspringbootjava.data.dto.v1.BookDTO;
import com.thonwelling.restwithspringbootjava.exceptions.ResourceNotFoundException;
import com.thonwelling.restwithspringbootjava.mapper.ModelMapperMapping;
import com.thonwelling.restwithspringbootjava.models.Book;
import com.thonwelling.restwithspringbootjava.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
  private final Logger logger = Logger.getLogger(BookService.class.getName());
  @Autowired
  BookRepository bookRepository;
  @Autowired
  ModelMapperMapping modelMapperMapping = new ModelMapperMapping();

  @Autowired
  PagedResourcesAssembler<BookDTO> assembler;
  public PagedModel<EntityModel<BookDTO>> getBooksList(Pageable pageable) {
    logger.info("Finding All Books !!!");
    var bookPage = bookRepository.findAll(pageable);
    var bookPageDto = bookPage.map(p -> modelMapperMapping.parseObject(p, BookDTO.class));
    bookPageDto.map(b -> {
      try {
        return b.add(linkTo(methodOn(BookController.class).getBookById(b.getId())).withSelfRel());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    Link link = linkTo(methodOn(BookController.class).getBooksList(pageable.getPageNumber(),  pageable.getPageSize(), "asc")).withSelfRel();
    return assembler.toModel(bookPageDto, link);
  }

  public BookDTO getBookById(Long id) throws Exception {
    logger.info("Finding A Book !!!");

    var entity = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    var dto = modelMapperMapping.parseObject(entity, BookDTO.class);
    dto.add(linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel());
    return dto;
  }

  public BookDTO createBook(BookDTO book) throws Exception {
    logger.info("Creating One Book !!!");
    var entity = modelMapperMapping.parseObject(book, Book.class);
    var dto = modelMapperMapping.parseObject(bookRepository.save(entity), BookDTO.class);
    dto.add(linkTo(methodOn(BookController.class).getBookById(dto.getId())).withSelfRel());
    return dto;

  }
  public BookDTO updateBook(BookDTO book) throws Exception {
    logger.info("Updating A Book !!!");
    var entity = bookRepository.findById(book.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    entity.setAuthor(book.getAuthor());
    entity.setLaunchDate(book.getLaunchDate());
    entity.setPrice(book.getPrice());
    entity.setTitle(book.getTitle());

    var dto = modelMapperMapping.parseObject(bookRepository.save(entity), BookDTO.class);
    dto.add(linkTo(methodOn(BookController.class).getBookById(dto.getId())).withSelfRel());
    return dto;
  }

  public void deleteBookById(Long id) {
    logger.info("Deleting One Book !!!");
    var entity = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    bookRepository.delete(entity);
  }
}