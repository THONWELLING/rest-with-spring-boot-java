package com.thonwelling.restwithspringbootjava.repositories;

import com.thonwelling.restwithspringbootjava.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  @Modifying
  @Query("UPDATE Person p  SET p.enabled = false WHERE p.id =:id")
  void disablePerson(@Param("id") Long id);

  /**
   * O LIKE muitas veze não é uma boa alternativa, pois, ele considera todos os registos,
   * em um caso de ter muitos registros pode er muito custoso em questão de performance para a sua aplicação.
   * neste caso poderia ter sido implementado uma busca `findByFirstName`, porém como é um sistem de estudo,
   * eu quero praticar usando querys SQL
   * */
  @Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT ('%',:firstName,'%'))")
  Page<Person> findPersonsByName(@Param("firstName") String firstName, Pageable pageable);
}
