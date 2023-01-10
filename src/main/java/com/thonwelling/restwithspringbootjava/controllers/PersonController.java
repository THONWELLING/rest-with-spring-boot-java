package com.thonwelling.restwithspringbootjava.controllers;


import com.thonwelling.restwithspringbootjava.data.dto.v1.PersonVO;
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
  public PersonVO getPersonById(@PathVariable(value = "id") Long id) throws Exception {
    return service.getPersonById(id);
  }
  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PersonVO> getPeopleList()  { return service.getPeopleList(); }

  @PostMapping(value = "/create",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO createPerson(@RequestBody PersonVO personVO) {
    return service.createPeson(personVO);
  }

  @PutMapping(value = "/update",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO updatePerson(@RequestBody PersonVO personVO) {
    return service.updatePerson(personVO);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deletePersonById(@PathVariable(value = "id") Long id) {
    service.deletePersonById(id);
    return ResponseEntity.noContent().build();
  }
}