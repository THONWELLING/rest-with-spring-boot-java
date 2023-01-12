package com.thonwelling.restwithspringbootjava.controllers;


import com.thonwelling.restwithspringbootjava.data.dto.v1.PersonDTO;
import com.thonwelling.restwithspringbootjava.data.dto.v2.PersonDTOV2;
import com.thonwelling.restwithspringbootjava.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/person/v1")
public class PersonController {
  @Autowired
  private PersonService service;
  @GetMapping(value = "/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public PersonDTO getPersonById(@PathVariable(value = "id") Long id) throws Exception {
    return service.getPersonById(id);
  }
  @GetMapping(value = "/all",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public List<PersonDTO> getPeopleList()  { return service.getPeopleList(); }

  @PostMapping(value = "/create",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public PersonDTO createPerson(@RequestBody PersonDTO person) {
    return service.createPerson(person);

  } @PostMapping(value = "/create/v2",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public PersonDTOV2 createPersonV2(@RequestBody PersonDTOV2 person) {
    return service.createPersonV2(person);
  }

  @PutMapping(value = "/update",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public PersonDTO updatePerson(@RequestBody PersonDTO personDTO) {
    return service.updatePerson(personDTO);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> deletePersonById(@PathVariable(value = "id") Long id) {
    service.deletePersonById(id);
    return ResponseEntity.noContent().build();
  }
}