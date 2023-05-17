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
import com.paymybuddy.paymybuddy.dao.user.PaymybuddyUserDao;
import com.paymybuddy.paymybuddy.dao.user.entities.PaymybuddyAppUser;
import com.paymybuddy.paymybuddy.dao.user.entities.PaymybuddyAppUserRole;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.CustomerAccountDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;

@Service
public class PaymybuddyAppUserBusiness implements UserDetailsService {
  
  private final PaymybuddyUserDao paymybuddyUserDao;
  
  private final PasswordEncoder passwordEncoder;
  
  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private CustomerAccountDao customerAccountDao;
  
  @Autowired
  public PaymybuddyAppUserBusiness(PaymybuddyUserDao paymybuddyUserDao, PasswordEncoder passwordEncoder) {
    this.paymybuddyUserDao = paymybuddyUserDao;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    return paymybuddyUserDao.findByUsername(username)
//        .orElseThrow( () -> new UsernameNotFoundException("Utilisateur non trouvé"));
    Optional<PaymybuddyAppUser> optPaymybuddyAppUser = paymybuddyUserDao.findByUsername(username);
    if (optPaymybuddyAppUser.isEmpty()) {
      throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.UserNotFound", username));
    }
    PaymybuddyAppUser paymybuddyAppUser = optPaymybuddyAppUser.get();
    
    return new org.springframework.security.core.userdetails.User(paymybuddyAppUser.getUsername(),
        paymybuddyAppUser.getPassword(),
        paymybuddyAppUser.getAuthorities());
  }
  
  /**
   * Add new user
   * 
   * @param register New user to add
   * @throws MyException Exception message 
   */
  @Transactional(rollbackFor = Exception.class)
  public void addUser(Register register) throws MyException {
    Optional<PaymybuddyAppUser> optPaymybuddyAppUser = paymybuddyUserDao.findByUsername(register.getEmail());
    if (optPaymybuddyAppUser.isEmpty() == false) {
      throw new MyException("throw.EmailAccountAlreadyeExists", register.getEmail());
    }
    
    //Add user
    PaymybuddyAppUser newPaymybuddyAppUser = new PaymybuddyAppUser();
    newPaymybuddyAppUser.setUsername(register.getEmail());
    newPaymybuddyAppUser.setPassword(passwordEncoder.encode(register.getPassword()));
    //
    // Modifier admin@admin.com !!!!!!!!!!!!!!!!!!!
    //
    if ("admin@admin.com".equals(register.getEmail())) {
      newPaymybuddyAppUser.setPaymybuddyAppUserRole(PaymybuddyAppUserRole.ADMIN_ROLE);
    } else {
      newPaymybuddyAppUser.setPaymybuddyAppUserRole(PaymybuddyAppUserRole.USER_ROLE);
    }
    newPaymybuddyAppUser = paymybuddyUserDao.save(newPaymybuddyAppUser);
    
    // Add customer
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setPaymybuddyAppUser(newPaymybuddyAppUser);
    customerEntity.setFirstName(register.getFirstName());
    customerEntity.setLastName(register.getLastName());
    customerEntity = customerDao.save(customerEntity);
    
    // Add customer account
    CustomerAccountEntity customerAccountEntity = new CustomerAccountEntity();
    customerAccountEntity.setIdCustomer(customerEntity.getId());
    customerAccountEntity.setBalance(0.0f);
    customerAccountDao.save(customerAccountEntity);
  }
}
