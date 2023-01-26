package com.thonwelling.restwithspringbootjava.controllers;


import com.thonwelling.restwithspringbootjava.data.dto.v1.PersonDTO;
import com.thonwelling.restwithspringbootjava.data.dto.v2.PersonDTOV2;
import com.thonwelling.restwithspringbootjava.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints To Manage People")
public class PersonController {
  @Autowired
  private PersonService service;

  @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @Operation(summary = "Finds a People By Id", description = "Finds a People By Id",
      tags = {"People"},
      responses = {
          @ApiResponse(description = "Success", responseCode = "200",
              content = @Content(schema = @Schema(implementation = PersonDTO.class))
          ),
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
      }
  )
  public PersonDTO getPersonById(@PathVariable(value = "id") Long id) throws Exception {
    return service.getPersonById(id);
  }

  @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @Operation(summary = "Finds All People", description = "Finds All People",
      tags = {"People"},
      responses = {
          @ApiResponse(description = "Success", responseCode = "200",
              content = {
                  @Content(
                      mediaType = "application/json",
                      array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                  )
              }),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
      }
  )
  public List<PersonDTO> getPeopleList() {
    return service.getPeopleList();
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Adds a New Person",
      description = "Adds a New Person By Passing In A JSON or XML Representation Of The Person",
      tags = {"People"},
      responses = {
          @ApiResponse(description = "Success", responseCode = "200",
              content = @Content(schema = @Schema(implementation = PersonDTO.class))
          ),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
      }
  )
  public PersonDTO createPerson(@RequestBody PersonDTO person) throws Exception {
    return service.createPerson(person);

  }

  @PostMapping(value = "/create/v2",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @Operation(summary = "Adds a New Person",
      description = "Adds a New Person By Passing In A JSON or XML Representation Of The Person With Birth Date",
      tags = {"People"},
      responses = {
          @ApiResponse(description = "Success", responseCode = "200",
              content = @Content(schema = @Schema(implementation = PersonDTO.class))
          ),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
      }
  )
  public PersonDTOV2 createPersonV2(@RequestBody PersonDTOV2 person) {
    return service.createPersonV2(person);
  }

  @PutMapping(value = "/update",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @Operation(summary = "Updates a Person",
      description = "Updates a Person By Passing In A JSON or XML Representation Of The Person",
      tags = {"People"},
      responses = {
          @ApiResponse(description = "Updated", responseCode = "200",
              content = @Content(schema = @Schema(implementation = PersonDTO.class))
          ),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
      }
  )
  public PersonDTO updatePerson(@RequestBody PersonDTO personDTO) throws Exception {
    return service.updatePerson(personDTO);
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Delets a Person",
      description = "Delets a Person By Passing In A Id",
      tags = {"People"},
      responses = {
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
      }
  )
  public ResponseEntity<?> deletePersonById(@PathVariable(value = "id") Long id) {
    service.deletePersonById(id);
    return ResponseEntity.noContent().build();
  }
}