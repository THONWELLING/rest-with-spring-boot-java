package com.thonwelling.restwithspringbootjava.repositories;

import com.thonwelling.restwithspringbootjava.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}
