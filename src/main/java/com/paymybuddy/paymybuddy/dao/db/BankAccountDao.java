package com.paymybuddy.paymybuddy.dao.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.BankAccountEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * BankAccountDao is the interface that manages BankAccountEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface BankAccountDao extends JpaRepository<BankAccountEntity, Integer> {

  public Optional<BankAccountEntity> findByCustomer(Optional<CustomerEntity> customerEntity);
  
}
