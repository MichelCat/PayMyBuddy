package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerBuddyEntity;


/**
 * CustomerBuddyDao is interface that manages the CustomerBuddyEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface CustomerBuddyDao {
  
  Page<CustomerBuddyEntity> findCustomerBuddyById(Integer id, Pageable pageable);
}
