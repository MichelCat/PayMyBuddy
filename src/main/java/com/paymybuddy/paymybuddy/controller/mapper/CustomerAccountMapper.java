package com.paymybuddy.paymybuddy.controller.mapper;

import org.mapstruct.Mapper;
import com.paymybuddy.paymybuddy.controller.model.CustomerAccount;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;

/**
 * Mapper CustomerAccountEntity to CustomerAccount
 * 
 * @param customerAccountEntity CustomerAccountEntity object
 * @return CustomerAccount
 */
@Mapper(componentModel = "spring")
public class CustomerAccountMapper {
  
  /**
   * Mapper CustomerAccountEntity to CustomerAccount
   * 
   * @param customerAccountEntity CustomerAccountEntity object
   * @return CustomerAccount
   */
  public CustomerAccount mapEntityToModel(CustomerAccountEntity customerAccountEntity) {
    if ( customerAccountEntity == null ) {
      return null;
    }
    
    CustomerAccount customerAccount = new CustomerAccount();
    customerAccount.setId(customerAccountEntity.getId());
    customerAccount.setIdCustomer(customerAccountEntity.getCustomer().getId());
    customerAccount.setBalance(customerAccountEntity.getBalance());
    return customerAccount;
  }
}
