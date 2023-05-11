package com.paymybuddy.paymybuddy.controller.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerBuddyEntity;

/**
 * CustomerBuddyUtils is an CustomerBuddy object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class CustomerBuddyUtils {

  /**
   * Conversion Object[] to CustomerBuddyEntity
   * 
   * @param object Object[] object
   * @return CustomerBuddyEntity
   */
  public CustomerBuddyEntity fromObjectToCustomerBuddyEntity(Object[] object) {
    CustomerBuddyEntity customerBuddyEntity = new CustomerBuddyEntity();
    customerBuddyEntity.setIdUser((Integer)object[0]);
    customerBuddyEntity.setIdBuddy((Integer)object[1]);
    customerBuddyEntity.setConnection((String)object[2]);
    customerBuddyEntity.setEmail((String)object[3]);
    return customerBuddyEntity;
  }
  
  /**
   * Conversion Object[] list to CustomerBuddyEntity list
   * 
   * @param objects Object[] list
   * @return CustomerBuddyEntity list
   */
  public List<CustomerBuddyEntity> fromListObjectToListCustomerBuddyEntity(List<Object[]> objects) {
    List<CustomerBuddyEntity> customerBuddyEntities = new ArrayList<>();
    for (Object[] object : objects) {
      customerBuddyEntities.add(fromObjectToCustomerBuddyEntity(object));
    }    
    return customerBuddyEntities;
  }
  
  /**
   * Conversion CustomerBuddyEntity to Buddy
   * 
   * @param customerBuddyEntity CustomerBuddyEntity object
   * @return Buddy
   */
  public Buddy fromCustomerBuddyEntityToBuddy(CustomerBuddyEntity customerBuddyEntity) {
    Buddy buddy = new Buddy();
    buddy.setIdUser(customerBuddyEntity.getIdUser());
    buddy.setIdBuddy(customerBuddyEntity.getIdBuddy());
    buddy.setConnection(customerBuddyEntity.getConnection());
    buddy.setEmail(customerBuddyEntity.getEmail());
    return buddy;
  }
  
  /**
   * Conversion CustomerBuddyEntity list to Buddy list
   * 
   * @param customerBuddyEntities CustomerBuddyEntity list
   * @return Buddy list
   */
  public List<Buddy> fromListCustomerBuddyEntityToListBuddy(List<CustomerBuddyEntity> customerBuddyEntities) {
    List<Buddy> buddies = new ArrayList<>();
    for (CustomerBuddyEntity customerBuddyEntity : customerBuddyEntities) {
      buddies.add(fromCustomerBuddyEntityToBuddy(customerBuddyEntity));
    }    
    return buddies;
  }
}
