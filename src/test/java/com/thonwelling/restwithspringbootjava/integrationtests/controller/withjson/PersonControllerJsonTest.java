package com.thonwelling.restwithspringbootjava.integrationtests.controller.withjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thonwelling.restwithspringbootjava.data.dto.v1.security.TokenDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.AccountCredentialsDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.PersonDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.testcontainers.AbstractIntegrationTest;
import configs.IntegrationTestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerJsonTest extends AbstractIntegrationTest {
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
    public void authorization() throws JsonProcessingException {
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
          .setBasePath("/api/person/v1")
          .setPort(IntegrationTestConfig.SERVER_PORT)
          .addFilter(new RequestLoggingFilter(LogDetail.ALL))
          .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
          .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
      mockPerson();

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
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

      assertEquals("Nelson", persistedPerson.getFirstName());
      assertEquals("Piquet", persistedPerson.getLastName());
      assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
      assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonProcessingException {
      person.setLastName("Piquet Souto Maior");

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
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

      assertEquals(person.getId(), persistedPerson.getId());

      assertEquals("Nelson", persistedPerson.getFirstName());
      assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
      assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
      assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonProcessingException {
      mockPerson();

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
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

      assertEquals(person.getId(), persistedPerson.getId());
      assertEquals("Nelson", persistedPerson.getFirstName());
      assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
      assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
      assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(4)
    public void testDelete() throws JsonProcessingException {

      given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
          .pathParam("id", person.getId())
          .when()
          .delete("{id}")
          .then()
          .statusCode(204);
    }

    @Test
    @Order(5)
    public void testFindAll() throws JsonProcessingException {

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
          .when()
          .get()
          .then()
          .statusCode(200)
          .extract()
          .body()
          .asString();

      List<PersonDTO> people = objectMapper.readValue(content, new TypeReference<List<PersonDTO>>() {});
      PersonDTO foundPersonOne = people.get(0);
      assertNotNull(foundPersonOne.getId());
      assertNotNull(foundPersonOne.getFirstName());
      assertNotNull(foundPersonOne.getLastName());
      assertNotNull(foundPersonOne.getAddress());
      assertNotNull(foundPersonOne.getGender());

      assertEquals(1, foundPersonOne.getId());
      assertEquals("Ayrton", foundPersonOne.getFirstName());
      assertEquals("Senna", foundPersonOne.getLastName());
      assertEquals("São Paulo", foundPersonOne.getAddress());
      assertEquals("Male", foundPersonOne.getGender());

      PersonDTO foundPersonSix = people.get(5);

      assertNotNull(foundPersonSix.getId());
      assertNotNull(foundPersonSix.getFirstName());
      assertNotNull(foundPersonSix.getLastName());
      assertNotNull(foundPersonSix.getAddress());
      assertNotNull(foundPersonSix.getGender());

      assertEquals(9, foundPersonSix.getId());

      assertEquals("Nelson", foundPersonSix.getFirstName());
      assertEquals("Mvezo", foundPersonSix.getLastName());
      assertEquals("Mvezo – South Africa", foundPersonSix.getAddress());
      assertEquals("Male", foundPersonSix.getGender());
    }


    @Test
    @Order(6)
    public void testFindAllWithoutToken() throws JsonProcessingException {

      RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
          .setBasePath("/api/person/v1")
          .setPort(IntegrationTestConfig.SERVER_PORT)
          .addFilter(new RequestLoggingFilter(LogDetail.ALL))
          .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
          .build();

      given().spec(specificationWithoutToken)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
          .when()
          .get()
          .then()
          .statusCode(403);
    }

    private void mockPerson() {
      person.setFirstName("Nelson");
      person.setLastName("Piquet");
      person.setAddress("Brasília - DF - Brasil");
      person.setGender("Male");
    }
  }