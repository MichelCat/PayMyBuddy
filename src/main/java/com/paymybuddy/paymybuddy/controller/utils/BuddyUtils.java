package com.paymybuddy.paymybuddy.controller.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;

/**
 * BuddyUtils is an Buddy object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class BuddyUtils {
  
  /**
   * Conversion BuddyEntity list to Buddy list
   * 
   * @param buddyEntities BuddyEntity list
   * @return Buddy list
   */
  public List<Buddy> fromListBuddyEntityToListBuddy(List<BuddyEntity> buddyEntities) {
    List<Buddy> buddies = new ArrayList<>();
    buddyEntities.forEach(e -> {
      buddies.add(fromBuddyEntityToBuddy(e));
    });
    return buddies;
  }
  
  /**
   * Conversion BuddyEntity to Buddy
   * 
   * @param buddyEntity BuddyEntity object
   * @return Buddy
   */
  public Buddy fromBuddyEntityToBuddy(BuddyEntity buddyEntity) {
    Buddy buddy = new Buddy();
    buddy.setId(buddyEntity.getId());
    buddy.setIdUser(buddyEntity.getCustomerUser().getId());
    buddy.setIdBuddy(buddyEntity.getCustomerBuddy().getId());
    buddy.setEmail("");
    buddy.setConnection(buddyEntity.getConnection());
    return buddy;
  }
  
  /**
   * Conversion Buddy to BuddyEntity
   * 
   * @param buddy Buddy object
   * @param customerUser CustomerEntity object
   * @param customerBuddy CustomerEntity object
   * @return BuddyEntity
   */
  public BuddyEntity fromBuddyToBuddyEntity(Buddy buddy) {
    BuddyEntity buddyEntity = new BuddyEntity();
    buddyEntity.setId(buddy.getId());
    buddyEntity.setCustomerUser(null);
    buddyEntity.setCustomerBuddy(null);
    buddyEntity.setConnection(buddy.getConnection());
    return buddyEntity;
  }
}
