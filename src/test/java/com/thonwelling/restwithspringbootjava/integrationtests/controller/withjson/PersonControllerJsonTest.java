package com.thonwelling.restwithspringbootjava.integrationtests.controller.withjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thonwelling.restwithspringbootjava.configs.IntegrationTestConfig;
import com.thonwelling.restwithspringbootjava.integrationtests.dto.PersonDTO;
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
class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;

	private static PersonDTO person;
	private static	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeAll
	public static void setup(){
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		person = new PersonDTO();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonProcessingException {
		mockPerson();
		specification = new RequestSpecBuilder()
				.addHeader(IntegrationTestConfig.HEADER_PARAM_ORIGIN, "https://thonwelling.com.br")
				.setBasePath("/api/person/v1/create")
				.setPort(IntegrationTestConfig.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		var content =
				given().spec(specification)
						.contentType(IntegrationTestConfig.CONTENT_TYPE_JSON)
						.body(person)
						.when()
						.post()
						.then()
							.statusCode(200)
						.extract()
							.body()
								.asString();

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		assertTrue(createdPerson.getId() > 0);

		assertEquals("Peter", createdPerson.getFirstName());
		assertEquals("Parker", createdPerson.getLastName());
		assertEquals("Queens, Nova Iorque, US", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
	}

	private void mockPerson() {
		person.setFirstName("Peter");
		person.setLastName("Parker");
		person.setAddress("Queens, Nova Iorque, US");
		person.setGender("Male");
	}
}
