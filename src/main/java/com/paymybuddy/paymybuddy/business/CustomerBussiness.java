package com.paymybuddy.paymybuddy.business;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.Customer;
import com.paymybuddy.paymybuddy.controller.utils.CustomerUtils;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

@Service
public class CustomerBussiness {

//  @Autowired
//  private CustomerDao customerDao;
//  @Autowired
//  private UserUtils userUtils;
//
//  public CustomerEntity getCusomerByEmail(String email) {
//    return customerDao.findByEmail(email);
//  }
//
//  public List<BuddyEntity> getBuddyEntitiesById(Integer id) {
//    return customerDao.findById(id).get().getBuddyEntityUsers();
//  }
//  
//  public List<User> getAllBuddiesCustomerById(Integer id) {
//    List<User> users = new ArrayList<>();
//    
//    List<BuddyEntity> buddyEntities = getBuddyEntitiesById(id);
//    buddyEntities.forEach(b -> {
//      users.add(userUtils.fromCustomerEntityToUser(b.getCustomerBuddy()));
//    });
//    return users;
//  }
//  
//  public CustomerEntity save(CustomerEntity customerEntity) {
//    return customerDao.save(customerEntity);
//  }
}
