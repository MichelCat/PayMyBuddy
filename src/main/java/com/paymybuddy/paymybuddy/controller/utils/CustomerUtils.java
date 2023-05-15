package com.paymybuddy.paymybuddy.controller.utils;

import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.Customer;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * UserUtils is an User object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class CustomerUtils {
  
  /**
   * Conversion CustomerEntity to User
   * 
   * @param customerEntity CustomerEntity object
   * @return User
   */
  public Customer fromCustomerEntityToUser(CustomerEntity customerEntity) {
    Customer customer = new Customer();
    customer.setId(customerEntity.getId());
    customer.setConnection("");
//    customer.setEmail(customerEntity.getEmail());
//    customer.setPassword(customerEntity.getPassword());
    customer.setFirstName(customerEntity.getFirstName());
    customer.setLastName(customerEntity.getLastName());
    customer.setAddress1(customerEntity.getAddress1());
    customer.setAddress2(customerEntity.getAddress2());
    customer.setZipCode(customerEntity.getZipCode());
    customer.setCity(customerEntity.getCity());
    return customer;
  }
  
  /**
   * Conversion User to CustomerEntity
   * 
   * @param customer User object
   * @return CustomerEntity
   */
  public CustomerEntity fromUserToCustomerEntity(Customer customer) {
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(customer.getId());
//    customerEntity.setEmail(customer.getEmail());
//    customerEntity.setPassword(customer.getPassword());
    customerEntity.setFirstName(customer.getFirstName());
    customerEntity.setLastName(customer.getLastName());
    customerEntity.setAddress1(customer.getAddress1());
    customerEntity.setAddress2(customer.getAddress2());
    customerEntity.setZipCode(customer.getZipCode());
    customerEntity.setCity(customer.getCity());
    return customerEntity;
  }
}
