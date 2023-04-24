package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * CustomerDao is the interface that manages CustomerEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface CustomerDao extends JpaRepository<CustomerEntity, Integer> {

  public CustomerEntity findByEmail(String email);
  
}
