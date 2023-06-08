package com.paymybuddy.paymybuddy.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.Exception.MyException;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.user.AppUserDao;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;

/**
 * EmailActivationBusiness is the email activation page processing service
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class EmailActivationBusiness {

  @Autowired
  private AppUserDao appUserDao;
  @Autowired
  private CustomerDao customerDao;


  /**
   * Account activation
   * 
   * @param validationKey Validation key for customers
   * @return New modified user record
   */
  @Transactional(rollbackFor = Exception.class)
  public AppUserEntity emailActivationBusiness(String validationKey) throws MyException {
    
    // User does not exist
    CustomerEntity customerEntity = customerDao.findByEmailValidationKey(validationKey)
        .orElseThrow(() -> new MyException("throw.CustomerNotExist"));
    
    // Invalid email key for customers
    if (!customerEntity.isValidEmailKey(validationKey)) {
      throw new MyException("throw.InvalidEmailKey");
    }
    // Email validation for customers time exceeded    
    if (!customerEntity.isValidEmailEndDate()) {
      throw new MyException("throw.EmailTimeExceeded");
    }
   
    // Account already activated
    AppUserEntity appUserEntity = customerEntity.getAppUserEntity();
    if (appUserEntity.isEnabled()) {
      throw new MyException("throw.AccountAlreadyActivated");
    }
    
    appUserEntity.setEnabled(true);
    return appUserDao.save(appUserEntity);
  }
}
