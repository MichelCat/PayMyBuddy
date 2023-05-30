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
