package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.exceptions.ResourceNotFoundException;
import com.thonwelling.restwithspringbootjava.models.Person;
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

  public List<Person> getPeopleList() {
    logger.info("Finding All People !!!");
    return repository.findAll();
  }


  public Person getPersonById(Long id) {
    logger.info("Finding A Person !!!");
    Person person = new Person();
    person.setId(id);
    person.setFirstName("Thonwelling");
    person.setLastName("Dani");
    person.setAddress("Guarulhos - São Paulo - Brasil");
    person.setGender("Male");
    return repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
  }

  public Person createPeson(Person person) {
    logger.info("Creating One Person !!!");
    return repository.save(person);
  }

  public Person updatePerson(Person person) {
    logger.info("Updating A Person !!!");
    Person entity = repository.findById(person.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    return repository.save(person);
  }

  public void deletePersonById(Long id) {
    logger.info("Deleting One Person !!!");
    Person entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    repository.delete(entity);
  }
}
