package com.paymybuddy.paymybuddy.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.Exception.MessagePropertieFormat;
import com.paymybuddy.paymybuddy.Exception.MyException;
import com.paymybuddy.paymybuddy.controller.model.CustomerUser;
import com.paymybuddy.paymybuddy.controller.utils.CustomerUserUtils;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerUserDao;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerUserEntity;
import com.paymybuddy.paymybuddy.dao.user.AppUserDao;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;

/**
 * SettingsBusiness is the settings page processing service
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class AdminCustomerBusiness {

  @Autowired
  private CustomerUserDao customerUserDao;
  @Autowired
  private CustomerUserUtils customerUserUtils;
  @Autowired
  private AppUserDao appUserDao;
  @Autowired
  private CustomerDao customerDao;
  
  /**
   * List of paginated customer users
   * 
   * @param pageNumber Current page
   * @param pageSize Page size
   * @return List of paginated client users
   */
  public Page<CustomerUser> getCustomerUserById(final int pageNumber, final int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    
    // Customer user
    Page<CustomerUserEntity> customerUserEntities = customerUserDao.findAllCustomerUser(pageable);
    
    List<CustomerUser> customerUsers = new ArrayList<>();
    customerUserEntities.getContent().forEach(b -> {
      CustomerUser customerUser = customerUserUtils.fromCustomerUserEntityToCustomerUser(b);
      customerUsers.add(customerUser);
    });
    return new PageImpl<>(customerUsers, pageable, customerUserEntities.getTotalElements());
  }
  
  /**
   * Update an existing customer user
   * 
   * @param customerUser The customer user object updated
   * @return New modified customer record
   */
  @Transactional(rollbackFor = Exception.class)
  public CustomerUser updateUser(CustomerUser customerUser) throws MyException {
    Optional<AppUserEntity> optAppUserEntity = appUserDao.findById((long)customerUser.getIdUser());
    if (optAppUserEntity.isEmpty()) {
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.UserNotFound", customerUser.getUsername()));
    }
    AppUserEntity appUserEntity = optAppUserEntity.get();
    
    appUserEntity.setUsername(customerUser.getUsername());
    appUserEntity.setExpired(customerUser.getExpired());
    appUserEntity.setLocked(customerUser.getLocked());
    appUserEntity.setCredentialsExpired(customerUser.getCredentialsExpired());
    appUserEntity.setEnabled(customerUser.getEnabled());
    appUserDao.save(appUserEntity);

    // User does not exist
    CustomerEntity customerEntity = customerDao.findByAppUserEntity(optAppUserEntity)
        .orElseThrow(() -> new MyException("throw.CustomerNotExist", customerUser.getUsername()));
    
    customerEntity.setFirstName(customerUser.getFirstName());
    customerEntity.setLastName(customerUser.getLastName());
    customerDao.save(customerEntity);
    
    return customerUser;
  }
  
  /**
   * Delete - Delete an customer user
   * 
   * @param IdUser - The id of the user to delete
   */
  @Transactional(rollbackFor = Exception.class)
  public void deleteUser(final Long IdUser) throws MyException {
    Optional<AppUserEntity> optAppUserEntity = appUserDao.findById(IdUser);
    if (optAppUserEntity.isEmpty()) {
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.UserNotFound", ""));
    }
    
    // User does not exist
    CustomerEntity customerEntity = customerDao.findByAppUserEntity(optAppUserEntity)
        .orElseThrow(() -> new MyException("throw.CustomerNotExist", ""));
    
    customerDao.deleteById(customerEntity.getId());
    
    appUserDao.deleteById(IdUser);
  }
}
