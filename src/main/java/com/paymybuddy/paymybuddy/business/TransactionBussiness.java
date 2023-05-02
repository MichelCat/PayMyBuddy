package com.paymybuddy.paymybuddy.business;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.MyException;
import com.paymybuddy.paymybuddy.controller.model.Transaction;
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
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;
import org.springframework.data.domain.PageImpl;

@Service
public class TransactionBussiness {
  
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
  private BuddyDao buddyDao;
  @Autowired
  private TransactionUtils transactionUtils;
  @Autowired
  private ResourceBundleMessageSource resourceBundleMessageSource;

  public Page<Transaction> getTransactionsById(final Integer id, final int pageNumber, final int pageSize) {
    List<Transaction> transactions = new ArrayList<>();
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    
    Optional<CustomerEntity> customerEntity = customerDao.findById(id);
    
    List<BuddyEntity> buddyEntities = buddyDao.findByCustomerUser(customerEntity);
    
    // Customer transactions
    Page<BankTransactionEntity> bankTransactionEntities = bankTransactionDao.findByIdNative(id, pageable);
    bankTransactionEntities.getContent().forEach(b -> {
      Transaction transaction = transactionUtils.fromBankTransactionEntityToTransaction(b);

      for (BuddyEntity buddyEntity : buddyEntities) {
        if (b.getCustomerCredit().getId().equals(id)) {
          // Credit transaction. Debtor search
          if (buddyEntity.getCustomerBuddy().getId().equals(b.getCustomerDebit().getId())) {
            transaction.setConnection(buddyEntity.getConnection());
            break;
          }
        } else {
          // Debit transaction. Creditor search
          if (buddyEntity.getCustomerBuddy().getId().equals(b.getCustomerCredit().getId())) {
            transaction.setConnection(buddyEntity.getConnection());
            break;
          }
        }
      }
      transactions.add(transaction);
    });
    
    return new PageImpl<>(transactions, pageable, bankTransactionEntities.getTotalElements());
  }
  
  @Transactional(rollbackFor = Exception.class)
  public Transaction addTransaction(Transaction transaction) throws MyException {
    // Transaction amount
    Float transactionAmount = transaction.getAmount();
    // Transaction levy
    Float levyRate = transactionParameterDao.findFirstByOrderByIdDesc().getLevyRate();
    Float transactionLevy = transactionAmount * levyRate;
    // Transaction date
    long currentTimeMillis = System.currentTimeMillis();
    Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
    // Debit Customer ID
    CustomerEntity customerDebit = customerDao.findById(transaction.getIdDebit()).get();
    // Credit Customer ID
    CustomerEntity customerCredit = customerDao.findById(transaction.getIdCredit()).get();
    
    // Customer account debit
    CustomerAccountEntity customerAccountDebit = customerAccountDao.findById(customerDebit.getId()).get();
    Float balance =customerAccountDebit.getBalance() - transactionAmount - transactionLevy;
    if (balance < 0) {
      throw new MyException(resourceBundleMessageSource, "throw.InsufficientMoneyInAccount");
    }
    customerAccountDebit.setBalance(balance);
    customerAccountDao.save(customerAccountDebit);
    
    // Customer account credit
    CustomerAccountEntity customerAccountCredit = customerAccountDao.findById(customerDebit.getId()).get();
    customerAccountCredit.setBalance(customerAccountCredit.getBalance() + transactionAmount);
    customerAccountDao.save(customerAccountCredit);
    
    // Transaction log
    TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
    transactionLogEntity.setCustomerDebit(customerDebit);
    transactionLogEntity.setCustomerCredit(customerCredit);
    transactionLogEntity.setLogDate(currentTimestamp);
    transactionLogEntity.setDescription(transaction.getDescription());
    transactionLogEntity.setAmount(transactionAmount);
    transactionLogEntity.setLevy(transactionLevy);
    transactionLogDao.save(transactionLogEntity);
    
    // Transaction
    transaction.setTransactionDate(currentTimestamp);
    transaction.setAmount(transactionAmount);
    transaction.setLevy(transactionLevy);
    
    BankTransactionEntity bankTransactionEntity = transactionUtils.fromTransactionToBankTransactionEntity(
                                                    transaction, customerDebit, customerCredit);
    return transactionUtils.fromBankTransactionEntityToTransaction(bankTransactionDao.save(bankTransactionEntity));
  }
}
