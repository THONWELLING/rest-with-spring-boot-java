package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.controllers.PersonController;
import com.thonwelling.restwithspringbootjava.data.dto.v1.PersonDTO;
import com.thonwelling.restwithspringbootjava.data.dto.v2.PersonDTOV2;
import com.thonwelling.restwithspringbootjava.exceptions.ResourceNotFoundException;
import com.thonwelling.restwithspringbootjava.mapper.DozerMapper;
import com.thonwelling.restwithspringbootjava.mapper.custom.PersonMapper;
import com.thonwelling.restwithspringbootjava.models.Person;
import com.thonwelling.restwithspringbootjava.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
  private final Logger logger = Logger.getLogger(PersonService.class.getName());
  @Autowired
  PersonRepository repository;
  @Autowired
  PersonMapper personMapper;

  public List<PersonDTO> getPeopleList() {
    logger.info("Finding All People !!!");
    var persons =  DozerMapper.parseListObjects(repository.findAll(), PersonDTO.class);
    persons.stream().forEach(p -> {
      try {
        p.add(linkTo(methodOn(PersonController.class).getPersonById(p.getKey())).withSelfRel());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    return persons;
  }

  public PersonDTO getPersonById(Long id) throws Exception {
    logger.info("Finding A Person !!!");

    var entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    var dto = DozerMapper.parseObject(entity, PersonDTO.class);
    dto.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());
    return dto;
  }

  public PersonDTO createPerson(PersonDTO person) throws Exception {
    logger.info("Creating One Person !!!");
    var entity = DozerMapper.parseObject(person, Person.class);
    var dto = DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
    dto.add(linkTo(methodOn(PersonController.class).getPersonById(dto.getKey())).withSelfRel());
    return dto;

  }  public PersonDTOV2 createPersonV2(PersonDTOV2 person) {
    logger.info("Creating One Person with V2!!!");
    var entity = personMapper.convertVoToEntity(person);
    return personMapper.convertEntityToVo(repository.save(entity));
  }

  public PersonDTO updatePerson(PersonDTO person) throws Exception {
    logger.info("Updating A Person !!!");
    var entity = repository.findById(person.getKey())
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    var dto = DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
    dto.add(linkTo(methodOn(PersonController.class).getPersonById(dto.getKey())).withSelfRel());
    return dto;
  }

  public void deletePersonById(Long id) {
    logger.info("Deleting One Person !!!");
    var entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    repository.delete(entity);
  }
}