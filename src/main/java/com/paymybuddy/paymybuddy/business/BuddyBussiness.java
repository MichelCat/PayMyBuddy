package com.paymybuddy.paymybuddy.business;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.utils.BuddyUtils;
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
  @Autowired
  private BuddyUtils buddyUtils;
  
  public List<Buddy> getBuddiesById(Integer id) {
    Optional<CustomerEntity> customerEntity = customerDao.findById(id);
    
    List<BuddyEntity> buddyEntities = buddyDao.findByCustomerUser(customerEntity);
    return buddyUtils.conversionListFireStationEntityToFireStation(buddyEntities);
  }
  
  @Transactional
  public Buddy addBuddy(Buddy buddy) {
    // Unknown email
    Integer idBuddy = customerDao.findByEmail(buddy.getEmail()).getId();
    if (idBuddy == null) {
      return null;
    }
    buddy.setIdBuddy(idBuddy);
    
    // Buddy already present
    if (buddyDao.findByCustomerUserAndCustomerBuddy(
                  customerDao.findById(buddy.getIdUser())
                  , customerDao.findById(idBuddy)) != null) {
      return null;
    }
    
    BuddyEntity buddyEntity = buddyUtils.fromBuddyToBuddyEntity(buddy
                                      , customerDao.findById(buddy.getIdUser()).get()
                                      , customerDao.findById(buddy.getIdBuddy()).get());
    return buddyUtils.fromBuddyEntityToBuddy(buddyDao.save(buddyEntity));
  }
}
