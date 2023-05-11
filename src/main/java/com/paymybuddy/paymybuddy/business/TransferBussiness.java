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
import com.paymybuddy.paymybuddy.controller.model.Transaction;
import com.paymybuddy.paymybuddy.controller.utils.BuddyUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionLogUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionParameterUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionUtils;
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
  private TransactionUtils transactionUtils;
  @Autowired
  private TransactionLogUtils transactionLogUtils;
  @Autowired
  private BuddyUtils buddyUtils;

  /**
   * Searching the User's Paginated Transaction List
   * 
   * @param id User ID
   * @param pageNumber Current page
   * @param pageSize Page size
   * @return List of user's paginated transactions
   */
  public Page<Transaction> getTransactionsById(final Integer id, final int pageNumber, final int pageSize) {
    List<Transaction> transactions = new ArrayList<>();
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    
    Optional<CustomerEntity> optCustomerEntity = customerDao.findById(id);
    
    // Buddy list
    List<BuddyEntity> buddyEntities = buddyDao.findByCustomerUser(optCustomerEntity);
    
    // Customer transactions
    Page<BankTransactionEntity> bankTransactionEntities = bankTransactionDao.findByIdNative(id, pageable);
    bankTransactionEntities.getContent().forEach(b -> {
      Transaction transaction = transactionUtils.fromBankTransactionEntityToTransaction(b);
      
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
          transaction.setConnection(buddyEntity.getConnection());
          break;
        }
      }
      transactions.add(transaction);
    });
    
    return new PageImpl<>(transactions, pageable, bankTransactionEntities.getTotalElements());
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
   * Adding the new transaction
   * 
   * @param transaction New transaction to add
   * @return New transaction added
   * @throws MyException Exception message 
   */
  @Transactional(rollbackFor = Exception.class)
  public Transaction addTransaction(Transaction transaction) throws MyException {
    // Transaction amount
    Float transactionAmount = transaction.getAmount();
    
    // Transaction levy
    TransactionParameterEntity transactionParameterEntity = transactionParameterDao.findFirstByOrderByEffectiveDateDesc()
        .orElseThrow(() -> new MyException("throw.TransactionParameterNotExist"));
    Float transactionLevy = transactionParameterUtils.roundLevy(transactionAmount * transactionParameterEntity.getLevyRate());
    
    // Transaction date
    long currentTimeMillis = System.currentTimeMillis();
    Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
    
    // Debit Customer ID
    CustomerEntity customerDebit = customerDao.findById(transaction.getIdDebit())
        .orElseThrow(() -> new MyException("throw.DebtorUserNotExist", transaction.getIdDebit()));
    
    // Credit Customer ID
    CustomerEntity customerCredit = customerDao.findById(transaction.getIdCredit())
        .orElseThrow(() -> new MyException("throw.CreditUserNotExist", transaction.getIdCredit()));
    
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
    BankTransactionEntity bankTransactionEntity = transactionUtils.fromTransactionToBankTransactionEntity(transaction);
    bankTransactionEntity.setCustomerDebit(customerDebit);
    bankTransactionEntity.setCustomerCredit(customerCredit);
    bankTransactionEntity.setTransactionDate(currentTimestamp);
    bankTransactionEntity.setAmount(transactionAmount);
    bankTransactionEntity.setLevy(transactionLevy);
    bankTransactionEntity = bankTransactionDao.save(bankTransactionEntity);
    
    // Transaction log
    TransactionLogEntity transactionLogEntity = transactionLogUtils.fromBankTransactionEntityToTransactionLogEntity(bankTransactionEntity);
    transactionLogDao.save(transactionLogEntity);
    
    return transactionUtils.fromBankTransactionEntityToTransaction(bankTransactionEntity);
  }
  
  /**
   * Adding the new connection
   * 
   * @param buddy New buddy to add
   * @return New buddy added
   * @throws MyException Exception message 
   */
  @Transactional(rollbackFor = Exception.class)
  public Buddy addBuddy(Buddy buddy) throws MyException {
    // Unknown email
    Optional<CustomerEntity> optCustomerBuddy = customerDao.findByEmail(buddy.getEmail());
    if (optCustomerBuddy.isEmpty()) {
      throw new MyException("throw.UnknownEmail", buddy.getEmail());
    }
    
    // Customer user
    Optional<CustomerEntity> optCustomerUser = customerDao.findById(buddy.getIdUser());
    
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
