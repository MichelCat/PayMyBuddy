package com.paymybuddy.paymybuddy.business;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.controller.model.BankAccount;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.User;
import com.paymybuddy.paymybuddy.controller.utils.BankAccountUtils;
import com.paymybuddy.paymybuddy.controller.utils.UserUtils;
import com.paymybuddy.paymybuddy.dao.db.BankAccountDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BankAccountEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

@Service
public class ProfileBussiness {
  
  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private UserUtils userUtils;
  @Autowired
  private BankAccountDao bankAccountDao;
  @Autowired
  private BankAccountUtils bankAccountUtils;

  public User getUserById(final Integer id) {
    CustomerEntity customerEntity = customerDao.findById(id).get();
    return userUtils.fromCustomerEntityToUser(customerEntity);
  }
  
  public BankAccount getBankAccountById(final Integer id) {
    BankAccountEntity bankAccountEntity = bankAccountDao.findById(id).get();
    return bankAccountUtils.fromBankAccountEntityToBankAccount(bankAccountEntity);
  }
  
  @Transactional
  public User changeUser(User user) {
    CustomerEntity customerEntity = userUtils.fromUserToCustomerEntity(user);
    return userUtils.fromCustomerEntityToUser(customerDao.save(customerEntity));
  }
  
  @Transactional
  public BankAccount changeBankAccount(BankAccount bankAccount) {
    Optional<CustomerEntity> customerEntity = customerDao.findById(bankAccount.getIdCustomer());
    BankAccountEntity bankAccountEntity = bankAccountUtils.fromBankAccountToBankAccountEntity(
                                            bankAccount, customerEntity.get());
    bankAccountEntity = bankAccountDao.save(bankAccountEntity);
    return bankAccountUtils.fromBankAccountEntityToBankAccount(bankAccountEntity);
  }
}
