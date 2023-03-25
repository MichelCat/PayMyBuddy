package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.PersonEntity;

/**
 * PersonDao is the interface that manages PersonEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface PersonDao extends JpaRepository<PersonEntity, Integer> {

  public PersonEntity findByPersonEmail(String personEmail);
  
  
  
}
