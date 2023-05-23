package com.paymybuddy.paymybuddy.business;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.Exception.MyException;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.BankTransaction;
import com.paymybuddy.paymybuddy.controller.utils.BuddyUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionLogUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionParameterUtils;
import com.paymybuddy.paymybuddy.controller.utils.BankTransactionUtils;
import com.paymybuddy.paymybuddy.dao.db.BankTransactionDao;
import com.paymybuddy.paymybuddy.dao.db.BuddyDao;
import com.paymybuddy.paymybuddy.dao.db.TransactionLogDao;
import com.paymybuddy.paymybuddy.dao.db.TransactionParameterDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerAccountDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionLogEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionParameterEntity;
import com.paymybuddy.paymybuddy.dao.user.AppUserDao;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;
import org.springframework.data.domain.PageImpl;

/**
 * TransferBussiness is the transfer page processing service
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class TransferBussiness {
  
  @Autowired
  private BankTransactionDao bankTransactionDao;
  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private TransactionLogDao transactionLogDao;
  @Autowired
  private CustomerAccountDao customerAccountDao;
  @Autowired
  private TransactionParameterDao transactionParameterDao;
  @Autowired
  private TransactionParameterUtils transactionParameterUtils;
  @Autowired
  private BuddyDao buddyDao;
  @Autowired
  private BankTransactionUtils bankTransactionUtils;
  @Autowired
  private TransactionLogUtils transactionLogUtils;
  @Autowired
  private BuddyUtils buddyUtils;
  @Autowired
  private AppUserDao appUserDao;
  
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
   * Searching the User's Paginated Transaction List
   * 
   * @param id User ID
   * @param pageNumber Current page
   * @param pageSize Page size
   * @return List of user's paginated transactions
   */
  public Page<BankTransaction> getTransactionsById(final Integer id, final int pageNumber, final int pageSize) {
    List<BankTransaction> bankTransactions = new ArrayList<>();
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    
    Optional<CustomerEntity> optCustomerEntity = customerDao.findById(id);
    
    // Buddy list
    List<BuddyEntity> buddyEntities = buddyDao.findByCustomerUser(optCustomerEntity);
    
    // Customer transactions
    Page<BankTransactionEntity> bankTransactionEntities = bankTransactionDao.findByIdNative(id, pageable);
    bankTransactionEntities.getContent().forEach(b -> {
      BankTransaction bankTransaction = bankTransactionUtils.fromBankTransactionEntityToBankTransaction(b);
      
      // Buddy ID
      Integer idBuddy;
      if (b.getCustomerCredit().getId().equals(id)) {
        // Credit transaction. Debtor search
        idBuddy = b.getCustomerDebit().getId();
      } else {
        // Debit transaction. Creditor search
        idBuddy = b.getCustomerCredit().getId();
      }

      // Connection search
      for (BuddyEntity buddyEntity : buddyEntities) {
        if (buddyEntity.getCustomerBuddy().getId().equals(idBuddy)) {
          bankTransaction.setConnection(buddyEntity.getConnection());
          break;
        }
      }
      bankTransactions.add(bankTransaction);
    });
    
    return new PageImpl<>(bankTransactions, pageable, bankTransactionEntities.getTotalElements());
  }
  
  /**
   * Search user's buddy list
   * 
   * @param id User ID
   * @return List of user's buddies
   */
  public List<Buddy> getBuddiesById(Integer id) {
    Optional<CustomerEntity> optCustomerEntity = customerDao.findById(id);
    
    List<BuddyEntity> buddyEntities = buddyDao.findByCustomerUser(optCustomerEntity);
    return buddyUtils.fromListBuddyEntityToListBuddy(buddyEntities);
  }
  
  /**
   * Adding the new BankTransaction
   * 
   * @param bankTransaction New BankTransaction to add
   * @return New transaction added
   * @throws MyException Exception message 
   */
  @Transactional(rollbackFor = Exception.class)
  public BankTransaction addTransaction(BankTransaction bankTransaction, String username) throws MyException {
    // Transaction amount
    Float transactionAmount = bankTransaction.getAmount();
    
    // Transaction levy
    TransactionParameterEntity transactionParameterEntity = transactionParameterDao.findFirstByOrderByEffectiveDateDesc()
        .orElseThrow(() -> new MyException("throw.TransactionParameterNotExist"));
    Float transactionLevy = transactionParameterUtils.roundLevy(transactionAmount * transactionParameterEntity.getLevyRate());
    
    // Transaction date
    long currentTimeMillis = System.currentTimeMillis();
    Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
    
    // Debit Customer ID    
    CustomerEntity customerDebit = customerDao.findByUsername(username)
        .orElseThrow(() -> new MyException("throw.DebtorUserNotExist", username));
    
    // Credit Customer ID
    CustomerEntity customerCredit = customerDao.findById(bankTransaction.getIdCredit())
        .orElseThrow(() -> new MyException("throw.CreditUserNotExist", bankTransaction.getIdCredit()));
    
    // Customer account debit
    CustomerAccountEntity customerAccountDebit = customerAccountDao.findById(customerDebit.getId())
        .orElseThrow(() -> new MyException("throw.DebtorAccountNotExist"));
    Float balance = customerAccountDebit.getBalance() - transactionAmount - transactionLevy;
    if (balance < 0) {
      throw new MyException("throw.InsufficientMoneyInAccount", customerAccountDebit.getBalance());
    }
    customerAccountDebit.setBalance(balance);
    customerAccountDao.save(customerAccountDebit);
    
    // Customer account credit
    CustomerAccountEntity customerAccountCredit = customerAccountDao.findById(customerCredit.getId())
        .orElseThrow(() -> new MyException("throw.CreditAccountNotExist"));
    customerAccountCredit.setBalance(customerAccountCredit.getBalance() + transactionAmount);
    customerAccountDao.save(customerAccountCredit);
    
    // Transaction
    BankTransactionEntity bankTransactionEntity = bankTransactionUtils.fromBankTransactionToBankTransactionEntity(bankTransaction);
    bankTransactionEntity.setCustomerDebit(customerDebit);
    bankTransactionEntity.setCustomerCredit(customerCredit);
    bankTransactionEntity.setTransactionDate(currentTimestamp);
    bankTransactionEntity.setAmount(transactionAmount);
    bankTransactionEntity.setLevy(transactionLevy);
    bankTransactionEntity = bankTransactionDao.save(bankTransactionEntity);
    
    // Transaction log
    TransactionLogEntity transactionLogEntity = transactionLogUtils.fromBankTransactionEntityToTransactionLogEntity(bankTransactionEntity);
    transactionLogDao.save(transactionLogEntity);
    
    return bankTransactionUtils.fromBankTransactionEntityToBankTransaction(bankTransactionEntity);
  }
  
  /**
   * Adding the new connection
   * 
   * @param buddy New buddy to add
   * @return New buddy added
   * @throws MyException Exception message 
   */
  @Transactional(rollbackFor = Exception.class)
  public Buddy addBuddy(Buddy buddy, String username) throws MyException {
    // Unknown email
    Optional<AppUserEntity> optAppUserEntity = appUserDao.findByUsername(buddy.getEmail());
    if (optAppUserEntity.isEmpty()) {
      throw new MyException("throw.UnknownEmail", buddy.getEmail());
    }
    
    // Customer user
    Optional<CustomerEntity> optCustomerUser = customerDao.findByUsername(username);
    
    // Customer user
    Optional<CustomerEntity> optCustomerBuddy = customerDao.findByAppUserEntity(optAppUserEntity);
    
    // Buddy already present
    Optional<BuddyEntity> optBuddyEntity = buddyDao.findByCustomerUserAndCustomerBuddy(optCustomerUser, optCustomerBuddy);
    if (optBuddyEntity.isEmpty() == false) {
      throw new MyException("throw.ConnectionAlreadyPresent", optBuddyEntity.get().getConnection());
    }
    
    BuddyEntity newBuddyEntity = buddyUtils.fromBuddyToBuddyEntity(buddy);
    newBuddyEntity.setCustomerUser(optCustomerUser.get());
    newBuddyEntity.setCustomerBuddy(optCustomerBuddy.get());
    return buddyUtils.fromBuddyEntityToBuddy(buddyDao.save(newBuddyEntity));
  }
}
