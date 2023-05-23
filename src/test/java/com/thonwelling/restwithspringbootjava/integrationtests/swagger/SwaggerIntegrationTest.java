package com.thonwelling.restwithspringbootjava.integrationtests.swagger;

import static io.restassured.RestAssured.given;

import com.thonwelling.configs.IntegrationTestConfig;
import com.thonwelling.restwithspringbootjava.integrationtests.testcontainers.AbstractIntegrationTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void shoudDisplaySwaggerUiPage() {
		var content =
				given()
						.basePath("/swagger-ui/index.html")
						.port(IntegrationTestConfig.SERVER_PORT)
						.when()
						.get()
						.then()
						.statusCode(200)
						.extract()
						.body()
						.asString();
		assertTrue(content.contains("Swagger UI"));
	}
}
