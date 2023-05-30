package com.paymybuddy.paymybuddy.business;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.Exception.MyException;
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


  /**
   * Account activation
   * 
   * @param validationKey Validation key for customers
   * @return New modified user record
   */
  @Transactional(rollbackFor = Exception.class)
  public AppUserEntity emailActivationBusiness(String validationKey) throws MyException {
    
    Optional<AppUserEntity> optAppUserEntity = appUserDao.findByEmailValidationKey(validationKey);
    // Key not found
    if (optAppUserEntity.isEmpty() == true) {
      throw new MyException("throw.EmailKeyNotFound");
    }
    
    AppUserEntity appUserEntity = optAppUserEntity.get();
    // Invalid email key for customers
    if (!appUserEntity.isValidEmailKey(validationKey)) {
      throw new MyException("throw.InvalidEmailKey");
    }
    // Email validation for customers time exceeded    
    if (!appUserEntity.isValidEmailEndDate()) {
      throw new MyException("throw.EmailTimeExceeded");
    }
    appUserEntity.setEnabled(true);
    return appUserDao.save(appUserEntity);
  }
}
