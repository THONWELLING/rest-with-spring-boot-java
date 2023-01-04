package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.models.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
  private final AtomicLong counter = new AtomicLong();
  private final Logger logger = Logger.getLogger(PersonService.class.getName());

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
}
