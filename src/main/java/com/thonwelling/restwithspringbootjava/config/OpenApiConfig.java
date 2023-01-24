package com.thonwelling.restwithspringbootjava.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customOPenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Restful API With Java 17 And Spring Boot 3")
            .version("v1")
            .description("Working in it!!")
            .termsOfService("https://github.com/THONWELLING/rest-with-spring-boot-java")
            .license(new License().name("Apache 2.0")
                .url("https://github.com/THONWELLING/rest-with-spring-boot-java"))
        );
  }
}
