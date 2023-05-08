package com.paymybuddy.paymybuddy.controller.utils;

import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.User;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * UserUtils is an User object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class UserUtils {
  
  /**
   * Conversion CustomerEntity to User
   * 
   * @param customerEntity CustomerEntity object
   * @return User
   */
  public User fromCustomerEntityToUser(CustomerEntity customerEntity) {
    User user = new User();
    user.setId(customerEntity.getId());
    user.setConnection("");
    user.setEmail(customerEntity.getEmail());
    user.setPassword(customerEntity.getPassword());
    user.setFirstName(customerEntity.getFirstName());
    user.setLastName(customerEntity.getLastName());
    user.setAddress1(customerEntity.getAddress1());
    user.setAddress2(customerEntity.getAddress2());
    user.setZipCode(customerEntity.getZipCode());
    user.setCity(customerEntity.getCity());
    return user;
  }
  
  /**
   * Conversion User to CustomerEntity
   * 
   * @param user User object
   * @return CustomerEntity
   */
  public CustomerEntity fromUserToCustomerEntity(User user) {
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(user.getId());
    customerEntity.setEmail(user.getEmail());
    customerEntity.setPassword(user.getPassword());
    customerEntity.setFirstName(user.getFirstName());
    customerEntity.setLastName(user.getLastName());
    customerEntity.setAddress1(user.getAddress1());
    customerEntity.setAddress2(user.getAddress2());
    customerEntity.setZipCode(user.getZipCode());
    customerEntity.setCity(user.getCity());
    return customerEntity;
  }
}
