package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.models.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
  private final AtomicLong counter = new AtomicLong();
  private final Logger logger = Logger.getLogger(PersonService.class.getName());

  public List<Person> findAll() {

    logger.info("Finding All People !!!");
    List<Person> persons = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      Person person = mockPerson(i);
      persons.add(person);
    }
    return persons;
  }


  public Person findById(String id) {

    logger.info("Finding A Person !!!");
    Person person = new Person();
    person.setId(counter.incrementAndGet());
    person.setFirstName("Thonwelling");
    person.setLastName("Dani");
    person.setAddress("Rua Santo Antônio");
    person.setGender("Male");

    return person;
  }

  public Person create(Person person) {
    logger.info("Creating One Person !!!");
    return person;
  }

  public Person update(Person person) {
    logger.info("Updating A Person !!!");
    return person;
  }

  public void delete(String id) {
    logger.info("Deleting One Person !!!");

  }
  private Person mockPerson(int i) {

    Person person = new Person();
    person.setId(counter.incrementAndGet());
    person.setFirstName("Person Name " + i);
    person.setLastName("Last Name " + i);
    person.setAddress("Person Address " + i);
    person.setGender("Male");
    return person;
  }
}
