package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.data.dto.v1.PersonDTO;
import com.thonwelling.restwithspringbootjava.models.Person;
import com.thonwelling.restwithspringbootjava.repositories.PersonRepository;
import com.thonwelling.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  MockPerson input;

  @InjectMocks
  private PersonService service;
  @Mock
  PersonRepository personRepository;

  @BeforeEach
  void setUpMocks() throws Exception {
    input = new MockPerson();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getPeopleList() {
    List<Person> list = input.mockEntityList();

    when(personRepository.findAll()).thenReturn(list);

    var people = service.getPeopleList();
    assertNotNull(people);
    assertEquals(14, people.size());

    var personOne = people.get(1);
    assertNotNull(personOne);
    assertNotNull(personOne.getKey());
    assertNotNull(personOne.getLinks());
//    System.out.println(result.toString());
    assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
    assertEquals("Addres Test1", personOne.getAddress());
    assertEquals("First Name Test1", personOne.getFirstName());
    assertEquals("Last Name Test1", personOne.getLastName());
    assertEquals("Female", personOne.getGender());

    var personSeven = people.get(7);
    assertNotNull(personSeven);
    assertNotNull(personSeven.getKey());
    assertNotNull(personSeven.getLinks());
//    System.out.println(result.toString());
    assertTrue(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));
    assertEquals("Addres Test7", personSeven.getAddress());
    assertEquals("First Name Test7", personSeven.getFirstName());
    assertEquals("Last Name Test7", personSeven.getLastName());
    assertEquals("Female", personSeven.getGender());

    var personTwelve = people.get(12);
    assertNotNull(personTwelve);
    assertNotNull(personTwelve.getKey());
    assertNotNull(personTwelve.getLinks());
//    System.out.println(result.toString());
    assertTrue(personTwelve.toString().contains("links: [</api/person/v1/12>;rel=\"self\"]"));
    assertEquals("Addres Test12", personTwelve.getAddress());
    assertEquals("First Name Test12", personTwelve.getFirstName());
    assertEquals("Last Name Test12", personTwelve.getLastName());
    assertEquals("Male", personTwelve.getGender());
  }

  @Test
  void getPersonById() throws Exception {
    Person entity = input.mockEntity(1);
    entity.setId(1L);

    when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

    var result = service.getPersonById(1L);
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());
//    System.out.println(result.toString());
    assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
    assertEquals("Addres Test1", result.getAddress());
    assertEquals("First Name Test1", result.getFirstName());
    assertEquals("Last Name Test1", result.getLastName());
    assertEquals("Female", result.getGender());
  }

  @Test
void createPerson() throws Exception {
    Person entity = input.mockEntity(1);
    entity.setId(1L);

    PersonDTO dto = input.mockDTO(1);
    dto.setKey(1L);

    when(personRepository.save(entity)).thenReturn(entity);

    var result = service.createPerson(dto);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
    assertEquals("Addres Test1", result.getAddress());
    assertEquals("First Name Test1", result.getFirstName());
    assertEquals("Last Name Test1", result.getLastName());
    assertEquals("Female", result.getGender());
  }

  @Test
  void updatePerson() throws Exception {
    Person entity = input.mockEntity(1);
    entity.setId(1L);

    PersonDTO dto = input.mockDTO(1);
    dto.setKey(1L);

    when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
    when(personRepository.save(entity)).thenReturn(entity);

    var result = service.updatePerson(dto);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
    assertEquals("Addres Test1", result.getAddress());
    assertEquals("First Name Test1", result.getFirstName());
    assertEquals("Last Name Test1", result.getLastName());
    assertEquals("Female", result.getGender());
  }

  @Test
  void deletePersonById() {
    Person entity = input.mockEntity(1);
    entity.setId(1L);

    when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

    service.deletePersonById(1L);
  }
}