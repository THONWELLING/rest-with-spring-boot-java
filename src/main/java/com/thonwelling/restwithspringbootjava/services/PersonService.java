package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.data.dto.v1.PersonVO;
import com.thonwelling.restwithspringbootjava.exceptions.ResourceNotFoundException;
import com.thonwelling.restwithspringbootjava.mapper.DozerMapper;
import com.thonwelling.restwithspringbootjava.models.Person;
import com.thonwelling.restwithspringbootjava.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
  private final Logger logger = Logger.getLogger(PersonService.class.getName());
  @Autowired
  PersonRepository repository;

  public List<PersonVO> getPeopleList() {
    logger.info("Finding All People !!!");
    return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
  }

  public PersonVO getPersonById(Long id) {
    logger.info("Finding A Person !!!");

    var entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    return DozerMapper.parseObject(entity, PersonVO.class);
  }

  public PersonVO createPeson(PersonVO person) {
    logger.info("Creating One Person !!!");
    var entity = DozerMapper.parseObject(person, Person.class);
    return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
  }

  public PersonVO updatePerson(PersonVO person) {
    logger.info("Updating A Person !!!");
    var entity = repository.findById(person.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
  }

  public void deletePersonById(Long id) {
    logger.info("Deleting One Person !!!");
    var entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No Records Found For This Id!!!"));
    repository.delete(entity);
  }
}
