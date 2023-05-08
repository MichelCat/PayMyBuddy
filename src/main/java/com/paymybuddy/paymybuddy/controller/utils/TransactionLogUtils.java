package com.paymybuddy.paymybuddy.controller.utils;

import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionLogEntity;

/**
 * TransactionLogUtils is an TransactionLog object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class TransactionLogUtils {
  
  /**
   * Conversion BankTransactionEntity to TransactionLogEntity
   * 
   * @param bankTransactionEntity BankTransactionEntity object
   * @return TransactionLogEntity
   */
  public TransactionLogEntity fromBankTransactionEntityToTransactionLogEntity(BankTransactionEntity bankTransactionEntity) {
    TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
    transactionLogEntity.setCustomerDebit(bankTransactionEntity.getCustomerDebit());
    transactionLogEntity.setCustomerCredit(bankTransactionEntity.getCustomerCredit());
    transactionLogEntity.setLogDate(bankTransactionEntity.getTransactionDate());
    transactionLogEntity.setDescription(bankTransactionEntity.getDescription());
    transactionLogEntity.setAmount(bankTransactionEntity.getAmount());
    transactionLogEntity.setLevy(bankTransactionEntity.getLevy());
    return transactionLogEntity;
  }
}
