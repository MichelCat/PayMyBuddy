package com.paymybuddy.paymybuddy.dao.db;

import java.util.List;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerBuddyEntity;


/**
 * CustomerBuddyDao is interface that manages the CustomerBuddyEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface CustomerBuddyDao {
  
  List<CustomerBuddyEntity> findCustomerBuddyById(Integer id);
}
