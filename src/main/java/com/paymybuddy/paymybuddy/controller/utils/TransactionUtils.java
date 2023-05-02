package com.paymybuddy.paymybuddy.controller.utils;

import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.Transaction;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * TransactionUtils is an Transaction object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class TransactionUtils {

  /**
   * Conversion BankTransactionEntity to Transaction
   * 
   * @param bankTransactionEntity BankTransactionEntity object
   * @return Transaction
   */
  public Transaction fromBankTransactionEntityToTransaction(BankTransactionEntity bankTransactionEntity) {
    Transaction transaction = new Transaction();
    transaction.setId(bankTransactionEntity.getId());
    transaction.setIdDebit(bankTransactionEntity.getCustomerDebit().getId());
    transaction.setIdCredit(bankTransactionEntity.getCustomerCredit().getId());
    transaction.setConnection("");
    transaction.setTransactionDate(bankTransactionEntity.getTransactionDate());
    transaction.setDescription(bankTransactionEntity.getDescription());
    transaction.setAmount(bankTransactionEntity.getAmount());
    transaction.setLevy(bankTransactionEntity.getLevy());
    return transaction;
  }
  
  /**
   * Conversion Transaction to BankTransactionEntity
   * 
   * @param transaction Transaction object
   * @param customerDebit CustomerEntity object
   * @param customerCredit CustomerEntity object
   * @return BankTransactionEntity
   */
  public BankTransactionEntity fromTransactionToBankTransactionEntity(Transaction transaction
                                , CustomerEntity customerDebit
                                , CustomerEntity customerCredit) {
    BankTransactionEntity bankTransactionEntity = new BankTransactionEntity();
    bankTransactionEntity.setId(transaction.getId());
    bankTransactionEntity.setCustomerDebit(customerDebit);
    bankTransactionEntity.setCustomerCredit(customerCredit);
    bankTransactionEntity.setTransactionDate(transaction.getTransactionDate());
    bankTransactionEntity.setDescription(transaction.getDescription());
    bankTransactionEntity.setAmount(transaction.getAmount());
    bankTransactionEntity.setLevy(transaction.getLevy());
    return bankTransactionEntity;
  }
}
