package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerUserEntity;

/**
 * CustomerUserDao is interface that manages the CustomerUserEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface CustomerUserDao {
  
  Page<CustomerUserEntity> findAllCustomerUser(Pageable pageable);
}
