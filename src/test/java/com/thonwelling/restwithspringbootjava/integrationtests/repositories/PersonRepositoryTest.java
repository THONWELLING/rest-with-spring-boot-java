package com.thonwelling.restwithspringbootjava.integrationtests.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.thonwelling.restwithspringbootjava.integrationtests.testcontainers.AbstractIntegrationTest;
import com.thonwelling.restwithspringbootjava.models.Person;
import com.thonwelling.restwithspringbootjava.repositories.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

  @Autowired
  PersonRepository repository;
  private static Person person;

  @BeforeAll
  public static void setup(){
    person = new Person();
  }

  @Test
  @Order(1)
  public void testFindByName() throws JsonMappingException, JsonProcessingException {

    Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.ASC, "firstName"));
    person = repository.findPersonsByName("tho", pageable).getContent().get(0);

    assertNotNull(person.getId());
    assertNotNull(person.getFirstName());
    assertNotNull(person.getLastName());
    assertNotNull(person.getAddress());
    assertNotNull(person.getGender());
    assertTrue(person.getEnabled());


    assertEquals(2, person.getId());

    assertEquals("Danithon", person.getFirstName());
    assertEquals("Steticist", person.getLastName());
    assertEquals("Rua Santo Antônio, 1336 - Guarulhos - São Paulo", person.getAddress());
    assertEquals("Female", person.getGender());
    assertEquals(true, person.getEnabled());
  }

  @Test
  @Order(2)
  public void testDisablePerson() throws JsonMappingException, JsonProcessingException {

    repository.disablePerson(person.getId());
    Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.ASC, "firstName"));
    person = repository.findPersonsByName("tho", pageable).getContent().get(0);

    assertNotNull(person.getId());
    assertNotNull(person.getFirstName());
    assertNotNull(person.getLastName());
    assertNotNull(person.getAddress());
    assertNotNull(person.getGender());
    assertFalse(person.getEnabled());


    assertEquals(2, person.getId());

    assertEquals("Danithon", person.getFirstName());
    assertEquals("Steticist", person.getLastName());
    assertEquals("Rua Santo Antônio, 1336 - Guarulhos - São Paulo", person.getAddress());
    assertEquals("Female", person.getGender());
    assertEquals(false, person.getEnabled());
  }
}
