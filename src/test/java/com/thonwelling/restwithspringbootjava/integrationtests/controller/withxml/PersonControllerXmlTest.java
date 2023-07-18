package com.thonwelling.restwithspringbootjava.integrationtests.controller.withxml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.thonwelling.configs.IntegrationTestConfig;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.AccountCredentialsDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.PersonDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.TokenDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.pagedmodel.PagedModelPerson;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.wrappers.WrapperPersonDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerXmlTest  extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static XmlMapper objectMapper;

    private static PersonDTO person;

    @BeforeAll
    public static void setup() {
      objectMapper = new XmlMapper();
      objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

      person = new PersonDTO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException {

      AccountCredentialsDTO user = new AccountCredentialsDTO("Thonwelling", "danithon");

      var accessToken = given()
          .basePath("/auth/signin")
          .port(IntegrationTestConfig.SERVER_PORT)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_XML)
          .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
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
    public void testCreate() throws JsonMappingException, JsonProcessingException {
      mockPerson();

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
          .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
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
      assertTrue(persistedPerson.getEnabled());

      assertTrue(persistedPerson.getId() > 0);

      assertEquals("Nelson", persistedPerson.getFirstName());
      assertEquals("Piquet", persistedPerson.getLastName());
      assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
      assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
      person.setLastName("Piquet Souto Maior");

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
          .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
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
      assertTrue(persistedPerson.getEnabled());

      assertEquals(person.getId(), persistedPerson.getId());
      assertEquals("Nelson", persistedPerson.getFirstName());
      assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
      assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
      assertEquals("Male", persistedPerson.getGender());
    }


    @Test
    @Order(3)
    public void testDisablePersonById() throws JsonMappingException, JsonProcessingException {

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
          .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
          .pathParam("id", person.getId())
          .when()
          .patch("{id}")
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
      assertFalse(persistedPerson.getEnabled());

      assertEquals(person.getId(), persistedPerson.getId());

      assertEquals("Nelson", persistedPerson.getFirstName());
      assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
      assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
      assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(4)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
      mockPerson();

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_XML)
          .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
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

      assertFalse(persistedPerson.getEnabled());

      assertEquals(person.getId(), persistedPerson.getId());

      assertEquals("Nelson", persistedPerson.getFirstName());
      assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
      assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
      assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(5)
    public void testDelete() throws JsonMappingException, JsonProcessingException {

      given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
          .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
          .pathParam("id", person.getId())
          .when()
          .delete("{id}")
          .then()
          .statusCode(204);
    }

    @Test
    @Order(6)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
          .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
          .queryParams("page", 3, "size", 10, "direction", "asc")
          .when()
          .get()
          .then()
          .statusCode(200)
          .extract()
          .body()
          .asString();

      PagedModelPerson wrapper = objectMapper.readValue(content, PagedModelPerson.class);
      var people = wrapper.getContent();
      PersonDTO foundPersonOne = people.get(0);

      assertNotNull(foundPersonOne);
      assertNotNull(foundPersonOne.getId());
      assertNotNull(foundPersonOne.getFirstName());
      assertNotNull(foundPersonOne.getLastName());
      assertNotNull(foundPersonOne.getGender());
      assertNotNull(foundPersonOne.getAddress());
      assertFalse(foundPersonOne.getEnabled());

      assertEquals(531, foundPersonOne.getId());

      assertEquals("Aloysia", foundPersonOne.getFirstName());
      assertEquals("Losseljong",  foundPersonOne.getLastName());
      assertEquals("3296 Hansons Alley", foundPersonOne.getAddress());
      assertEquals("Female", foundPersonOne.getGender());
      assertEquals(false, foundPersonOne.getEnabled());

      PersonDTO foundPersonSix = people.get(5);

      assertNotNull(foundPersonSix.getId());
      assertNotNull(foundPersonSix.getFirstName());
      assertNotNull(foundPersonSix.getLastName());
      assertNotNull(foundPersonSix.getAddress());
      assertNotNull(foundPersonSix.getGender());
      assertTrue(foundPersonSix.getEnabled());

      assertTrue(foundPersonSix.getEnabled());

      assertEquals(1022, foundPersonSix.getId());

      assertEquals("Amalea", foundPersonSix.getFirstName());
      assertEquals("Kennady", foundPersonSix.getLastName());
      assertEquals("5388 Golf Course Lane", foundPersonSix.getAddress());
      assertEquals("Female", foundPersonSix.getGender());
      assertEquals(true, foundPersonSix.getEnabled());
    }

    @Test
    @Order(7)
    public void testFindByName() throws JsonMappingException, JsonProcessingException {

      var content = given().spec(specification)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
          .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
          .pathParam("firstName", "tho")
          .queryParams("page", 0, "size", 6, "direction", "asc")
          .when()
          .get("findPersonByName/{firstName}")
          .then()
          .statusCode(200)
          .extract()
          .body()
          .asString();

      PagedModelPerson wrapper = objectMapper.readValue(content, PagedModelPerson.class);
      var people = wrapper.getContent();

      PersonDTO foundPersonOne = people.get(0);

      assertNotNull(foundPersonOne.getId());
      assertNotNull(foundPersonOne.getFirstName());
      assertNotNull(foundPersonOne.getLastName());
      assertNotNull(foundPersonOne.getAddress());
      assertNotNull(foundPersonOne.getGender());
      assertTrue(foundPersonOne.getEnabled());

      assertEquals(2, foundPersonOne.getId());
      assertEquals("Danithon", foundPersonOne.getFirstName());
      assertEquals("Steticist", foundPersonOne.getLastName());
      assertEquals("Rua Santo Antônio, 1336 - Guarulhos - São Paulo", foundPersonOne.getAddress());
      assertEquals("Female", foundPersonOne.getGender());
      assertEquals(true, foundPersonOne.getEnabled());;
    }

    @Test
    @Order(8)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

      RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
          .setBasePath("/api/person/v1")
          .setPort(IntegrationTestConfig.SERVER_PORT)
          .addFilter(new RequestLoggingFilter(LogDetail.ALL))
          .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
          .build();

      given().spec(specificationWithoutToken)
          .contentType(IntegrationTestConfig.CONTENT_TYPE_XML)
          .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
          .when()
          .get()
          .then()
          .statusCode(403);
    }

  @Test
  @Order(9)
  public void testHATEOAS() throws JsonMappingException, JsonProcessingException {

    var content = given().spec(specification)
        .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
        .accept(IntegrationTestConfig.CONTENT_TYPE_XML)
        .queryParams("page", 3, "size", 10, "direction", "asc")
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/531</href></links>"));
    assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/957</href></links>"));
    assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/416</href></links>"));

    assertTrue(content.contains("<links><rel>first</rel><href>http://localhost:8888/api/person/v1?direction=asc&amp;page=0&amp;size=10&amp;sort=firstName,asc</href></links>"));
    assertTrue(content.contains("<links><rel>prev</rel><href>http://localhost:8888/api/person/v1?direction=asc&amp;page=2&amp;size=10&amp;sort=firstName,asc</href></links>"));
    assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1?page=3&amp;size=10&amp;direction=asc</href></links>"));
    assertTrue(content.contains("<links><rel>last</rel><href>http://localhost:8888/api/person/v1?direction=asc&amp;page=102&amp;size=10&amp;sort=firstName,asc</href></links>"));

    assertTrue(content.contains("<page><size>10</size><totalElements>1023</totalElements><totalPages>103</totalPages><number>3</number></page>"));
  }
    private void mockPerson() {
        person.setFirstName("Nelson");
        person.setLastName("Piquet");
        person.setAddress("Brasília - DF - Brasil");
        person.setGender("Male");
        person.setEnabled(true);
    }
}
