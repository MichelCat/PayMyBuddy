package com.paymybuddy.paymybuddy.business;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.Exception.MessagePropertieFormat;
import com.paymybuddy.paymybuddy.Exception.MyException;
import com.paymybuddy.paymybuddy.controller.model.AppUserPrincipal;
import com.paymybuddy.paymybuddy.controller.model.Register;
import com.paymybuddy.paymybuddy.dao.user.AppUserDao;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserRole;
import com.paymybuddy.paymybuddy.utils.EmailBusiness;
import lombok.extern.slf4j.Slf4j;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.CustomerAccountDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;

@Slf4j
@Service
public class AppUserBusiness implements UserDetailsService {
  
  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private CustomerAccountDao customerAccountDao;
  @Autowired
  private AppUserDao appUserDao;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private EmailBusiness emailBusiness;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    log.trace("Entering method loadUserByUsername");
    log.debug("Authentication user email: " + username);
    
    Optional<AppUserEntity> optAppUserEntity = appUserDao.findByUsername(username);
    if (optAppUserEntity.isEmpty()) {
      log.error(MessagePropertieFormat.getMessage("throw.UserNotFound", username));
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.UserNotFound", username));
    }
    AppUserEntity appUserEntity = optAppUserEntity.get();
    
    // Activated user
    if (!appUserEntity.isEnabled()) {
      log.info(MessagePropertieFormat.getMessage("throw.AccountNotActivated", username));
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountNotActivated", username));
    }
    // User account expired
    if (!appUserEntity.isAccountNonExpired()) {
      log.info(MessagePropertieFormat.getMessage("throw.AccountExpired", username));
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountExpired", username));
    }
    // User locked
    if (!appUserEntity.isAccountNonLocked()) {
      log.info(MessagePropertieFormat.getMessage("throw.AccountLocked", username));
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountLocked", username));
    }
    // User credentials (password) expired
    if (!appUserEntity.isCredentialsNonExpired()) {
      log.info(MessagePropertieFormat.getMessage("throw.PasswordExpired", username));
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.PasswordExpired", username));
    }
    
    return new AppUserPrincipal(appUserEntity);
    
//    return new org.springframework.security.core.userdetails.User(appUserEntity.getUsername(),
//        appUserEntity.getPassword(),
//        appUserEntity.getAuthorities());
    
//    return new org.springframework.security.core.userdetails.User(
//        appUserEntity.getUsername(),
//        appUserEntity.getPassword(),
//        appUserEntity.isEnabled(),
//        appUserEntity.isCredentialsNonExpired(),
//        appUserEntity.isAccountNonExpired(),
//        appUserEntity.isAccountNonLocked(),
//        appUserEntity.getAuthorities());
//    }
    
  }
  
  /**
   * Add new user
   * 
   * @param register New user to add
   * @throws MyException Exception message 
   */
  @Transactional(rollbackFor = Exception.class)
  public void addCustomer(Register register) throws MyException {
    Optional<AppUserEntity> optAppUserEntity = appUserDao.findByUsername(register.getEmail());
    if (optAppUserEntity.isEmpty() == false) {
      throw new MyException("throw.EmailAccountAlreadyeExists", register.getEmail());
    }
    
    //Add user
    AppUserEntity newAppUserEntity = new AppUserEntity();
    newAppUserEntity.setUsername(register.getEmail());
    newAppUserEntity.setPassword(passwordEncoder.encode(register.getPassword()));
    newAppUserEntity.setAppUserRole(AppUserRole.USER_ROLE.name());
    newAppUserEntity = appUserDao.save(newAppUserEntity);
    
    // Add customer
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setAppUserEntity(newAppUserEntity);
    customerEntity.setFirstName(register.getFirstName());
    customerEntity.setLastName(register.getLastName());
    customerEntity.createValidEmailKey();
    customerEntity.createValidEndDate();
    customerEntity = customerDao.save(customerEntity);
    
    // Add customer account
    CustomerAccountEntity customerAccountEntity = new CustomerAccountEntity();
    customerAccountEntity.setCustomer(customerEntity);
    customerAccountEntity.setBalance(0.0f);
    customerAccountDao.save(customerAccountEntity);
    
    // Contact email does not exist
    AppUserEntity userContactEmail = appUserDao.findByContactEmail()
        .orElseThrow(() -> new MyException("throw.ContactEmailNotExist"));
    
    // Send activation email contact
    String subject = "Activation Email";
    String message = "You can activate your account using the link : "
                      + "http://localhost:8080/register/" + customerEntity.getEmailValidationKey();
    emailBusiness.sendEmail(userContactEmail.getUsername(), register.getEmail(), subject, message);
  }
}
