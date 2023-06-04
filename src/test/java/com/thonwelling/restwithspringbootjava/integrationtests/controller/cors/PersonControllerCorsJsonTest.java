package com.thonwelling.restwithspringbootjava.integrationtests.controller.cors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thonwelling.restwithspringbootjava.data.dto.v1.security.TokenDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.AccountCredentialsDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.PersonDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.testcontainers.AbstractIntegrationTest;
import com.thonwelling.configs.IntegrationTestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerCorsJsonTest extends AbstractIntegrationTest {
  private static RequestSpecification specification;
  private static ObjectMapper objectMapper;

  private static PersonDTO person;

  @BeforeAll
  public static void setup() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    person = new PersonDTO();
  }

  @Test
  @Order(0)
  public void authorization() throws JsonMappingException, JsonProcessingException {

    AccountCredentialsDTO user = new AccountCredentialsDTO("Thonwelling", "thondani");

    var accessToken = given()
    .basePath("/auth/signin")
    .port(IntegrationTestConfig.SERVER_PORT)
    .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
    .body(user)
    .when()
    .post()
    .then()
    .statusCode(200)
    .extract()
    .body()
    .as(TokenDTO.class)
    .getAccessToken();

    specification = new RequestSpecBuilder()
    .addHeader(IntegrationTestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
    .setBasePath("/api/person/v1/create")
    .setPort(IntegrationTestConfig.SERVER_PORT)
    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
    .build();
  }

  @Test
  @Order(1)
  public void testCreate() throws JsonMappingException, JsonProcessingException {
    mockPerson();

    var content = given().spec(specification)
    .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
    .header(IntegrationTestConfig.HEADER_PARAM_ORIGIN, IntegrationTestConfig.ORIGIN_THONWELLING)
    .body(person)
    .when()
    .post()
    .then()
    .statusCode(200)
    .extract()
    .body()
    .asString();

    PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
    person = persistedPerson;

    assertNotNull(persistedPerson);
    assertNotNull(persistedPerson.getId());
    assertNotNull(persistedPerson.getFirstName());
    assertNotNull(persistedPerson.getLastName());
    assertNotNull(persistedPerson.getAddress());
    assertNotNull(persistedPerson.getGender());
    assertTrue(persistedPerson.getId() > 0);
    assertEquals("Richard", persistedPerson.getFirstName());
    assertEquals("Stallman", persistedPerson.getLastName());
    assertEquals("New York City, New York, US", persistedPerson.getAddress());
    assertEquals("Male", persistedPerson.getGender());
  }

  @Test
  @Order(2)
  public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
    mockPerson();

    var content = given().spec(specification)
    .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
    .header(IntegrationTestConfig.HEADER_PARAM_ORIGIN, IntegrationTestConfig.ORIGIN_WELLINGTHON)
    .body(person)
    .when()
    .post()
    .then()
    .statusCode(403)
    .extract()
    .body()
    .asString();

    assertNotNull(content);
    assertEquals("Invalid CORS request", content);
  }

  @Test
  @Order(3)
  public void testFindById() throws JsonMappingException, JsonProcessingException {
    mockPerson();

    var content = given().spec(specification)
    .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
    .header(IntegrationTestConfig.HEADER_PARAM_ORIGIN, IntegrationTestConfig.ORIGIN_THONWELLING)
    .pathParam("id", person.getId())
    .when()
    .get("{id}")
    .then()
    .statusCode(200)
    .extract()
    .body()
    .asString();

    PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
    person = persistedPerson;
    assertNotNull(persistedPerson);
    assertNotNull(persistedPerson.getId());
    assertNotNull(persistedPerson.getFirstName());
    assertNotNull(persistedPerson.getLastName());
    assertNotNull(persistedPerson.getAddress());
    assertNotNull(persistedPerson.getGender());
    assertTrue(persistedPerson.getId() > 0);
    assertEquals("Richard", persistedPerson.getFirstName());
    assertEquals("Stallman", persistedPerson.getLastName());
    assertEquals("New York City, New York, US", persistedPerson.getAddress());
    assertEquals("Male", persistedPerson.getGender());
  }

  @Test
  @Order(4)
  public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
    mockPerson();
    var content = given().spec(specification)
    .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
    .header(IntegrationTestConfig.HEADER_PARAM_ORIGIN, IntegrationTestConfig.ORIGIN_WELLINGTHON)
    .pathParam("id", person.getId())
    .when()
    .get("{id}")
    .then()
    .statusCode(403)
    .extract()
    .body()
    .asString();

    assertNotNull(content);
    assertEquals("Invalid CORS request", content);
  }

  @Test
  @Order(5)
  public void testDelete() throws JsonMappingException, JsonProcessingException {

    given().spec(specification)
    .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
    .pathParam("id", person.getId())
    .when()
    .delete("{id}")
    .then()
    .statusCode(204);
  }

  private void mockPerson() {
    person.setFirstName("Richard");
    person.setLastName("Stallman");
    person.setAddress("New York City, New York, US");
    person.setGender("Male");
    person.setEnabled(true);
  }
}
