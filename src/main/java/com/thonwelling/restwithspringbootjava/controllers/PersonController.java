package com.thonwelling.restwithspringbootjava.controllers;


import com.thonwelling.restwithspringbootjava.data.dto.v1.PersonDto;
import com.thonwelling.restwithspringbootjava.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/person")
public class PersonController {
  @Autowired
  private PersonService service;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonDto getPersonById(@PathVariable(value = "id") Long id) throws Exception {
    return service.getPersonById(id);
  }
  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PersonDto> getPeopleList()  { return service.getPeopleList(); }

  @PostMapping(value = "/create",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public PersonDto createPerson(@RequestBody PersonDto personDto) {
    return service.createPeson(personDto);
  }

  @PutMapping(value = "/update",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public PersonDto updatePerson(@RequestBody PersonDto personDto) {
    return service.updatePerson(personDto);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deletePersonById(@PathVariable(value = "id") Long id) {
    service.deletePersonById(id);
    return ResponseEntity.noContent().build();
  }
}