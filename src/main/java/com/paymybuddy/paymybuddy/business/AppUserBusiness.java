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
import com.paymybuddy.paymybuddy.controller.model.Register;
import com.paymybuddy.paymybuddy.dao.user.AppUserDao;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserRole;
import com.paymybuddy.paymybuddy.utils.EmailBusiness;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.CustomerAccountDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;

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
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<AppUserEntity> optAppUserEntity = appUserDao.findByUsername(username);
    if (optAppUserEntity.isEmpty()) {
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.UserNotFound", username));
    }
    AppUserEntity appUserEntity = optAppUserEntity.get();
    
    // Activated user
    if (!appUserEntity.isEnabled()) {
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountNotActivated"));
    }
    // User account expired
    if (!appUserEntity.isAccountNonExpired()) {
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountExpired"));
    }
    // User locked
    if (!appUserEntity.isAccountNonLocked()) {
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountLocked"));
    }
    // User credentials (password) expired
    if (!appUserEntity.isCredentialsNonExpired()) {
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.PasswordExpired"));
    }
    
    return new org.springframework.security.core.userdetails.User(appUserEntity.getUsername(),
        appUserEntity.getPassword(),
        appUserEntity.getAuthorities());
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
    newAppUserEntity.createValidEmailKey();
    newAppUserEntity.createValidEndDate();
    newAppUserEntity.setAppUserRole(AppUserRole.USER_ROLE.name());
    newAppUserEntity = appUserDao.save(newAppUserEntity);
    
    // Add customer
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setAppUserEntity(newAppUserEntity);
    customerEntity.setFirstName(register.getFirstName());
    customerEntity.setLastName(register.getLastName());
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
                      + "http://localhost:8080/register/" + newAppUserEntity.getEmailValidationKey();
    emailBusiness.sendEmail(userContactEmail.getUsername(), register.getEmail(), subject, message);
  }
}
