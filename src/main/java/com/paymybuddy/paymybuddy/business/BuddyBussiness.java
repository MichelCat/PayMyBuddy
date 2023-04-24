package com.paymybuddy.paymybuddy.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.dao.db.BuddyDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

@Service
public class BuddyBussiness {

  @Autowired
  private BuddyDao buddyDao;
  @Autowired
  private CustomerDao customerDao;
  
  public List<Buddy> getBuddiesById(Integer id) {
    List<Buddy> buddies = new ArrayList<>();

    Optional<CustomerEntity> customerEntity = customerDao.findById(id);
    
    List<BuddyEntity> buddyEntities = buddyDao.findByCustomerUser(customerEntity);
    buddyEntities.forEach(b -> {
      buddies.add(from(b));
    });
    return buddies;
  }
  
  private Buddy from(BuddyEntity buddyEntity) {
    Buddy buddy = new Buddy();
    buddy.setId(buddyEntity.getId());
    buddy.setIdUser(buddyEntity.getCustomerUser().getId());
    buddy.setIdBuddy(buddyEntity.getCustomerBuddy().getId());
    buddy.setEmail("");
    buddy.setConnection(buddyEntity.getConnection());
    return buddy;
  }
  
  private BuddyEntity from(Buddy buddy) {
    BuddyEntity buddyEntity = new BuddyEntity();
    buddyEntity.setId(buddy.getId());
    buddyEntity.setCustomerUser(customerDao.findById(buddy.getIdUser()).get());
    buddyEntity.setCustomerBuddy(customerDao.findById(buddy.getIdBuddy()).get());
    buddyEntity.setConnection(buddy.getConnection());
    return buddyEntity;
  }
  
  
  public Buddy addBuddy(Buddy buddy) {
    Integer idBuddy = customerDao.findByEmail(buddy.getEmail()).getId();
    if (idBuddy == null) {
      return null;
    }
    buddy.setIdBuddy(idBuddy);
    return from(buddyDao.save(from(buddy)));
  }
}
