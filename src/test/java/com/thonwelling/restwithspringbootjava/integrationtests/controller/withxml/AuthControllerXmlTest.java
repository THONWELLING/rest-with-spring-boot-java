package com.thonwelling.restwithspringbootjava.integrationtests.controller.withxml;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.thonwelling.restwithspringbootjava.data.dto.v1.security.TokenDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.AccountCredentialsDTO;
import com.thonwelling.restwithspringbootjava.integrationtests.testcontainers.AbstractIntegrationTest;
import configs.IntegrationTestConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {
  private static TokenDTO tokenDTO;

  @Test
  @Order(1)
  public void testSignin() throws JsonMappingException, JsonProcessingException {

    AccountCredentialsDTO user =
        new AccountCredentialsDTO("Thonwelling", "thondani");

    tokenDTO = given()
        .basePath("/auth/signin")
        .port(IntegrationTestConfig.SERVER_PORT)
        .contentType(IntegrationTestConfig.CONTENT_TYPE_XML)
        .body(user)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .as(TokenDTO.class);
    assertNotNull(tokenDTO.getAccessToken());
    assertNotNull(tokenDTO.getRefreshToken());
  }

  @Test
  @Order(2)
  public void testRefresh() throws JsonMappingException, JsonProcessingException {

    var newTokenDto = given()
        .basePath("/auth/refresh")
        .port(IntegrationTestConfig.SERVER_PORT)
        .contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
        .pathParam("username", tokenDTO.getUsername())
        .header(IntegrationTestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDTO.getRefreshToken())
        .when()
        .put("{username}")
        .then()
        .statusCode(200)
        .extract()
        .body()
        .as(TokenDTO.class);

    assertNotNull(newTokenDto.getAccessToken());
    assertNotNull(newTokenDto.getRefreshToken());
  }
}
