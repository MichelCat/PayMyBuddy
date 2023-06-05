package com.paymybuddy.paymybuddy.controller.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.CustomerUser;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerUserEntity;

/**
 * CustomerUserUtils is an CustomerUser object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class CustomerUserUtils {
  
  /**
   * Conversion CustomerUserEntity to CustomerUser
   * 
   * @param customerUserEntity CustomerUserEntity object
   * @return CustomerUser
   */
  public CustomerUser fromCustomerUserEntityToCustomerUser(CustomerUserEntity customerUserEntity) {
    CustomerUser customerUser = new CustomerUser();
    customerUser.setIdUser(customerUserEntity.getIdUser());
    customerUser.setUsername(customerUserEntity.getUsername());
    customerUser.setFirstName(customerUserEntity.getFirstName());
    customerUser.setLastName(customerUserEntity.getLastName());
    customerUser.setAppUserRole(customerUserEntity.getAppUserRole());
    customerUser.setExpired(customerUserEntity.getExpired());
    customerUser.setLocked(customerUserEntity.getLocked());
    customerUser.setCredentiaExpired(customerUserEntity.getCredentiaExpired());
    customerUser.setEnabled(customerUserEntity.getEnabled());
    customerUser.setEmailValidationKey(customerUserEntity.getEmailValidationKey());
    customerUser.setValidEmailEndDate(customerUserEntity.getValidEmailEndDate());
    return customerUser;
  }
  
  /**
   * Conversion CustomerUserEntity list to CustomerUser list
   * 
   * @param customerUserEntities CustomerUserEntity list
   * @return CustomerUser list
   */
  public List<CustomerUser> fromListCustomerUserEntityToListCustomerUser(List<CustomerUserEntity> customerUserEntities) {
    List<CustomerUser> customerUsers = new ArrayList<>();
    for (CustomerUserEntity customerUserEntity : customerUserEntities) {
      customerUsers.add(fromCustomerUserEntityToCustomerUser(customerUserEntity));
    }    
    return customerUsers;
  }
}
