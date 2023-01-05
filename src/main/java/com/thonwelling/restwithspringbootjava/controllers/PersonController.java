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
  public Person getPersonById(@PathVariable(value = "id") Long id) throws Exception {
    return service.getPersonById(id);
  }
  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Person> getPeopleList()  { return service.getPeopleList(); }

  @PostMapping(value = "/create",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Person createPerson(@RequestBody Person person) {
    return service.createPeson(person);
  }

  @PutMapping(value = "/update",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Person updatePerson(@RequestBody Person person) {
    return service.updatePerson(person);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deletePersonById(@PathVariable(value = "id") Long id) {
     service.deletePersonById(id);
  }
}

