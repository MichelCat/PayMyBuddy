package com.paymybuddy.paymybuddy.business;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.Customer;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

@Service
public class CustomerBussiness {

  @Autowired
  private CustomerDao customerDao;

  public CustomerEntity getCusomerByEmail(String email) {
    return customerDao.findByEmail(email);
  }

  public List<BuddyEntity> getBuddyEntitiesById(Integer id) {
    return customerDao.findById(id).get().getBuddyEntityUsers();
  }
  
  public List<Customer> getAllBuddiesCustomerById(Integer id) {
    List<Customer> customers = new ArrayList<>();
    
    List<BuddyEntity> buddyEntities = getBuddyEntitiesById(id);
    buddyEntities.forEach(b -> {
      customers.add(from(b.getCustomerBuddy()));
    });
    return customers;
  }
  
  public Customer from(CustomerEntity customerEntity) {
    Customer customer = new Customer();
    customer.setId(customerEntity.getId());
    customer.setEmail(customerEntity.getEmail());
    customer.setPassword(customerEntity.getPassword());
    return customer;
  }
  
  public CustomerEntity save(CustomerEntity customerEntity) {
    return customerDao.save(customerEntity);
  }
}
