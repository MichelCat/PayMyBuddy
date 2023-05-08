package com.paymybuddy.paymybuddy.controller.utils;

import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.CustomerAccount;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;

/**
 * CustomerAccountUtils is an CustomerAccount object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class CustomerAccountUtils {

  /**
   * Conversion CustomerAccountEntity to CustomerAccount
   * 
   * @param customerAccountEntity CustomerAccountEntity object
   * @return CustomerAccount
   */
  public CustomerAccount fromCustomerAccountEntityToCustomerAccount(CustomerAccountEntity customerAccountEntity) {
    CustomerAccount customerAccount = new CustomerAccount();
    customerAccount.setIdCustomer(customerAccountEntity.getIdCustomer());
    customerAccount.setBalance(customerAccountEntity.getBalance());
    return customerAccount;
  }
}
