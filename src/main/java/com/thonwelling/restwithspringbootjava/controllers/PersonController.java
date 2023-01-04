package com.thonwelling.restwithspringbootjava.controllers;


import com.thonwelling.restwithspringbootjava.models.Person;
import com.thonwelling.restwithspringbootjava.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  private PersonService service;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Person findById(@PathVariable(value = "id") String id) throws Exception {
    return service.findById(id);
  }
  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Person> findAll()  { return service.findAll(); }

  @PostMapping(value = "/create",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Person create(@RequestBody Person person) {
    return service.create(person);
  }

  @PutMapping(value = "/update",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Person update(@RequestBody Person person) {
    return service.update(person);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteById(@PathVariable(value = "id") String id) {
     service.delete(id);
  }
}

