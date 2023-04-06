package com.thonwelling.restwithspringbootjava.integrationtests.controller.withjson;

import com.thonwelling.restwithspringbootjava.data.dto.v1.security.AccountCredentialsDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.BookDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.TokenDTO;
import configs.IntegrationTestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerJsonTest {
  private static RequestSpecification specification;
  private static ObjectMapper objectMapper;

  private static BookDTO book;

  @BeforeAll
  public static void setup() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    book = new BookDTO();
  }

  @Test
  @Order(1)
  public void authorization() {
    AccountCredentialsDTO user = new AccountCredentialsDTO();
    user.setUsername("Thonwelling");
    user.setPassword("thondani");

    var token = given()
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
        .addHeader(IntegrationTestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + token)
        .setBasePath("/api/book/v1")
        .setPort(IntegrationTestConfig.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
  }

  @Test
  @Order(2)
  public void testCreate() throws JsonProcessingException {
    mockBook();
    var content = given().spec(specification)
        .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
        .body(book)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    book = objectMapper.readValue(content, BookDTO.class);

    assertNotNull(book.getKey());
    assertNotNull(book.getTitle());
    assertNotNull(book.getAuthor());
    assertNotNull(book.getPrice());
    assertTrue(book.getKey() > 0);
    assertEquals("Docker Deep Dive", book.getTitle());
    assertEquals("Nigel Poulton", book.getAuthor());
    assertEquals(55.99, book.getPrice());
  }

  @Test
  @Order(3)
  public void testUpdate() throws JsonProcessingException {

    book.setTitle("Docker Deep Dive - Updated");

    var content = given().spec(specification)
        .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
        .body(book)
        .when()
        .put()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    BookDTO bookUpdated = objectMapper.readValue(content, BookDTO.class);

    assertNotNull(bookUpdated.getKey());
    assertNotNull(bookUpdated.getTitle());
    assertNotNull(bookUpdated.getAuthor());
    assertNotNull(bookUpdated.getPrice());
    assertEquals(bookUpdated.getKey(), book.getKey());
    assertEquals("Docker Deep Dive - Updated", bookUpdated.getTitle());
    assertEquals("Nigel Poulton", bookUpdated.getAuthor());
    assertEquals(55.99, bookUpdated.getPrice());
  }

  @Test
  @Order(4)
  public void testFindById() throws JsonProcessingException {
    var content = given().spec(specification)
        .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
        .pathParam("id", book.getKey())
        .when()
        .get("{id}")
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    BookDTO foundBook = objectMapper.readValue(content, BookDTO.class);

    assertNotNull(foundBook.getKey());
    assertNotNull(foundBook.getTitle());
    assertNotNull(foundBook.getAuthor());
    assertNotNull(foundBook.getPrice());
    assertEquals(foundBook.getKey(), book.getKey());
    assertEquals("Docker Deep Dive - Updated", foundBook.getTitle());
    assertEquals("Nigel Poulton", foundBook.getAuthor());
    assertEquals(55.99, foundBook.getPrice());
  }

  @Test
  @Order(5)
  public void testDelete() {
    given().spec(specification)
        .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
        .pathParam("id", book.getKey())
        .when()
        .delete("{id}")
        .then()
        .statusCode(204);
  }

  @Test
  @Order(6)
  public void testFindAll() throws JsonProcessingException {

    var content = given().spec(specification)
        .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
        .queryParams("page", 0, "limit", 5, "direction", "asc")
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    List<BookDTO> books = objectMapper.readValue(content, new TypeReference<List<BookDTO>>() {
    });

    BookDTO foundBookOne = books.get(0);
    assertNotNull(foundBookOne.getKey());
    assertNotNull(foundBookOne.getTitle());
    assertNotNull(foundBookOne.getAuthor());
    assertNotNull(foundBookOne.getPrice());
    assertTrue(foundBookOne.getKey() > 0);
    assertEquals("Working effectively with legacy code", foundBookOne.getTitle());
    assertEquals("Michael C. Feathers", foundBookOne.getAuthor());
    assertEquals(49.00, foundBookOne.getPrice());

    BookDTO foundBookFive = books.get(4);

    assertNotNull(foundBookFive.getKey());
    assertNotNull(foundBookFive.getTitle());
    assertNotNull(foundBookFive.getAuthor());
    assertNotNull(foundBookFive.getPrice());
    assertTrue(foundBookFive.getKey() > 0);
    assertEquals("Code complete", foundBookFive.getTitle());
    assertEquals("Steve McConnell", foundBookFive.getAuthor());
    assertEquals(58.0, foundBookFive.getPrice());
  }

  private void mockBook() {
    book.setTitle("Docker Deep Dive");
    book.setAuthor("Nigel Poulton");
    book.setPrice(55.99);
    book.setLaunchDate(new Date());
  }
}
