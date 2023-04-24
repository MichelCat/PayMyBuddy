package com.paymybuddy.paymybuddy.dao.db;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * BuddyDao is the interface that manages BuddyEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface BuddyDao extends JpaRepository<BuddyEntity, Integer> {

  public List<BuddyEntity> findByCustomerUser(Optional<CustomerEntity> customerEntity);
  
}
