package com.thonwelling.restwithspringbootjava.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

   static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres");

    private static void  startContainers(){
      Startables.deepStart(Stream.of(postgresql)).join();
    }
    private static Map<String, String> createConnectionConfiguration() {
      return Map.of(
          "spring.datasource.url", postgresql.getJdbcUrl(),
          "spring.datasource.username", postgresql.getUsername(),
          "spring.datasource.password", postgresql.getPassword()
      );
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      startContainers();
      ConfigurableEnvironment enviroment = applicationContext.getEnvironment();
      MapPropertySource testcontainers = new MapPropertySource(
          "testcontainers", (Map) createConnectionConfiguration());
      enviroment.getPropertySources().addFirst(testcontainers);
    }
  }
}