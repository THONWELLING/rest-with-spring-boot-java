package com.thonwelling.restwithspringbootjava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {

  @Value("${cors.originPatterns:default}")
  private  String corsOriginPatterns = "";

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    var allowedOrigins = corsOriginPatterns.split(",");
    registry.addMapping("/**")
//    .allowedMethods("GET","POST","PUT") /**Para permitir cors por metodos HTTP*/
        .allowedMethods("*")
        .allowedOrigins(allowedOrigins)
        .allowCredentials(true);
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    WebMvcConfigurer.super.configureContentNegotiation(configurer);

    /* Via Query Param
      configurer.favorParameter(true)
        .parameterName("mediaType")
        .ignoreAcceptHeader(true)
        .useRegisteredExtensionsOnly(false)
        .defaultContentType(MediaType.APPLICATION_JSON)
          .mediaType("json", MediaType.APPLICATION_JSON)
          .mediaType("xml", MediaType.APPLICATION_XML);
     */

    configurer.favorParameter(false)
        .ignoreAcceptHeader(false)
        .useRegisteredExtensionsOnly(false)
        .defaultContentType(MediaType.APPLICATION_JSON)
          .mediaType("json", MediaType.APPLICATION_JSON)
          .mediaType("xml", MediaType.APPLICATION_XML);
  }
}
