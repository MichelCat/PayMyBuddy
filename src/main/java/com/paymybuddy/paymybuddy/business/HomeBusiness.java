package com.paymybuddy.paymybuddy.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.Exception.MyException;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.CustomerAccount;
import com.paymybuddy.paymybuddy.controller.model.BankTransaction;
import com.paymybuddy.paymybuddy.controller.model.TransactionParameter;
import com.paymybuddy.paymybuddy.controller.utils.CustomerAccountUtils;
import com.paymybuddy.paymybuddy.controller.utils.CustomerBuddyUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionParameterUtils;
import com.paymybuddy.paymybuddy.controller.utils.BankTransactionUtils;
import com.paymybuddy.paymybuddy.dao.db.BankTransactionDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerAccountDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.TransactionParameterDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerBuddyDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionParameterEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerBuddyEntity;

/**
 * HomeBussiness is the home page processing service
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class HomeBusiness {

  @Autowired
  private TransactionParameterDao transactionParameterDao;
  @Autowired
  private TransactionParameterUtils transactionParameterUtils;
  @Autowired
  private CustomerAccountDao customerAccountDao;
  @Autowired
  private CustomerAccountUtils customerAccountUtils;
  @Autowired
  private BankTransactionDao bankTransactionDao;
  @Autowired
  private BankTransactionUtils bankTransactionUtils;
  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private CustomerBuddyDao customerBuddyDao;
  @Autowired
  private CustomerBuddyUtils customerBuddyUtils;
  
  /**
   * Find customer ID
   * 
   * @param username Email
   * @return Customer ID
   */
  public Integer getCustomerId(final String username) throws MyException {
    // User does not exist
    CustomerEntity customerEntity = customerDao.findByUsername(username)
        .orElseThrow(() -> new MyException("throw.CustomerNotExist", username));
    return customerEntity.getId();
  }

  /**
   * Find the list of transaction parameters
   * 
   * @return List of transaction parameters sorted by descending effective date
   */
  public Page<TransactionParameter> getTransactionParameters(final int pageNumber, final int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    
    Page<TransactionParameterEntity> transactionParameterEntities = transactionParameterDao.findAllByOrderByEffectiveDateDesc(pageable);
    
    List<TransactionParameter> transactionParameters = new ArrayList<>();
    transactionParameterEntities.getContent().forEach(b -> {
      TransactionParameter transactionParameter = transactionParameterUtils.fromTransactionParameterEntityToTransactionParameter(b);
      transactionParameters.add(transactionParameter);
    });
    
    return new PageImpl<>(transactionParameters, pageable, transactionParameterEntities.getTotalElements());
  }
  
  /**
   * Find customer account information
   * 
   * @param id User ID
   * @return Customer account information
   */
  public CustomerAccount getCustomerAccountById(final Integer id) throws MyException {
    Optional<CustomerAccountEntity> optCustomerAccountEntity = customerAccountDao.findById(id);
    if (optCustomerAccountEntity.isEmpty()) {
      throw new MyException("throw.UnknownBankAccount");
    }
    return customerAccountUtils.fromCustomerAccountEntityToCustomerAccount(optCustomerAccountEntity.get());
  }
  
  /**
   * Last transaction
   * 
   * @param id User ID
   * @return List of last transaction
   */
  public List<BankTransaction> getLastTransactionById(final Integer id) {
    Optional<CustomerEntity> optCustomerEntity = customerDao.findById(id);
    
    List<BankTransactionEntity> bankTransactionEntities
                              = bankTransactionDao.findFirst2ByCustomerDebitOrCustomerCreditOrderByTransactionDateDesc(
                                   optCustomerEntity, optCustomerEntity);
    
    List<BankTransaction> bankTransactions = new ArrayList<>();
    bankTransactionEntities.forEach(b -> {
      BankTransaction bankTransaction = bankTransactionUtils.fromBankTransactionEntityToBankTransaction(b);
      
      if (b.getCustomerDebit().getId().equals(id)) {
        // Debit transaction.
        bankTransaction.setAmount(-bankTransaction.getAmount());
      }
      bankTransactions.add(bankTransaction);
    });
    
    return bankTransactions;
  }
  
  /**
   * Search user's buddy list
   * 
   * @param id User ID
   * @param pageNumber Current page
   * @param pageSize Page size
   * @return List of user's buddies
   */
  public Page<Buddy> getBuddiesById(Integer id, final int pageNumber, final int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    
    Page<CustomerBuddyEntity> customerBuddyEntities = customerBuddyDao.findCustomerBuddyById(id, pageable);
    
    List<Buddy> buddies = new ArrayList<>();
    customerBuddyEntities.getContent().forEach(b -> {
      Buddy buddy = customerBuddyUtils.fromCustomerBuddyEntityToBuddy(b);
      buddies.add(buddy);
    });
    return new PageImpl<>(buddies, pageable, customerBuddyEntities.getTotalElements());
  }
}
