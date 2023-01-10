package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.data.dto.v1.PersonDto;
import com.thonwelling.restwithspringbootjava.exceptions.ResourceNotFoundException;
import com.thonwelling.restwithspringbootjava.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
  private final Logger logger = Logger.getLogger(PersonService.class.getName());
  @Autowired
  PersonRepository repository;

  public List<PersonDto> getPeopleList() {
    logger.info("Finding All People !!!");
    return repository.findAll();
  }


  public PersonDto getPersonById(Long id) {
    logger.info("Finding A Person !!!");
    PersonDto personDto = new PersonDto();
    personDto.setId(id);
    personDto.setFirstName("Thonwelling");
    personDto.setLastName("Dani");
    personDto.setAddress("Guarulhos - São Paulo - Brasil");
    personDto.setGender("Male");
    return repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
  }

  public PersonDto createPeson(PersonDto personDto) {
    logger.info("Creating One Person !!!");
    return repository.save(personDto);
  }

  public PersonDto updatePerson(PersonDto personDto) {
    logger.info("Updating A Person !!!");
    PersonDto entity = repository.findById(personDto.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    entity.setFirstName(personDto.getFirstName());
    entity.setLastName(personDto.getLastName());
    entity.setAddress(personDto.getAddress());
    entity.setGender(personDto.getGender());

    return repository.save(personDto);
  }

  public void deletePersonById(Long id) {
    logger.info("Deleting One Person !!!");
    PersonDto entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    repository.delete(entity);
  }
}
