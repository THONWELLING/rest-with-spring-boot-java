package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.controllers.PersonController;
import com.thonwelling.restwithspringbootjava.data.dto.v1.PersonDTO;
import com.thonwelling.restwithspringbootjava.data.dto.v2.PersonDTOV2;
import com.thonwelling.restwithspringbootjava.exceptions.ResourceNotFoundException;
import com.thonwelling.restwithspringbootjava.mapper.DozerMapper;
import com.thonwelling.restwithspringbootjava.mapper.custom.PersonMapper;
import com.thonwelling.restwithspringbootjava.models.Person;
import com.thonwelling.restwithspringbootjava.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
  private final Logger logger = Logger.getLogger(PersonService.class.getName());
  @Autowired
  PersonRepository personRepository;
  @Autowired
  PersonMapper personMapper;
@Autowired
PagedResourcesAssembler<PersonDTO> assembler;

  public PagedModel<EntityModel<PersonDTO>> getPeopleList(Pageable pageable) {
    logger.info("Finding All People !!!");

    var personPage = personRepository.findAll(pageable);
    var personPageDto = personPage.map(p -> DozerMapper.parseObject(p, PersonDTO.class));
    personPageDto.map(p -> {
      try {
        return p.add(linkTo(methodOn(PersonController.class).getPersonById(p.getKey())).withSelfRel());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    Link link = linkTo(methodOn(PersonController.class).getPeopleList(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
    return assembler.toModel(personPageDto, link);
  }

  public PersonDTO getPersonById(Long id) throws Exception {
    logger.info("Finding A Person !!!");

    var entity = personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    var dto = DozerMapper.parseObject(entity, PersonDTO.class);
    dto.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());
    return dto;
  }

  public PersonDTO createPerson(PersonDTO person) throws Exception {
    logger.info("Creating One Person !!!");
    var entity = DozerMapper.parseObject(person, Person.class);
    var dto = DozerMapper.parseObject(personRepository.save(entity), PersonDTO.class);
    dto.add(linkTo(methodOn(PersonController.class).getPersonById(dto.getKey())).withSelfRel());
    return dto;

  }  public PersonDTOV2 createPersonV2(PersonDTOV2 person) {
    logger.info("Creating One Person with V2!!!");
    var entity = personMapper.convertVoToEntity(person);
    return personMapper.convertEntityToVo(personRepository.save(entity));
  }

  public PersonDTO updatePerson(PersonDTO person) throws Exception {
    logger.info("Updating A Person !!!");
    var entity = personRepository.findById(person.getKey())
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    var dto = DozerMapper.parseObject(personRepository.save(entity), PersonDTO.class);
    dto.add(linkTo(methodOn(PersonController.class).getPersonById(dto.getKey())).withSelfRel());
    return dto;
  }
  @Transactional
  public PersonDTO disablePerson(Long id) throws Exception {
    logger.info("Disabling A Person !!!");

    personRepository.disablePerson(id);
    var entity = personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    var dto = DozerMapper.parseObject(entity, PersonDTO.class);
    dto.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());
    return dto;
  }

  public void deletePersonById(Long id) {
    logger.info("Deleting One Person !!!");
    var entity = personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    personRepository.delete(entity);
  }
}