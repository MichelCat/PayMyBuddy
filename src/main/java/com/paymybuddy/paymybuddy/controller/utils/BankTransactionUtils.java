package com.paymybuddy.paymybuddy.controller.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.BankTransaction;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;

/**
 * TransactionUtils is an Transaction object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class BankTransactionUtils {
  
  /**
   * Conversion BankTransactionEntity list to BankTransaction list
   * 
   * @param bankTransactionEntities BankTransactionEntity list
   * @return BankTransaction list
   */
  public List<BankTransaction> fromListBankTransactionEntityToListBankTransaction(List<BankTransactionEntity> bankTransactionEntities) {
    List<BankTransaction> bankTransactions = new ArrayList<>();
    for (BankTransactionEntity bankTransactionEntity : bankTransactionEntities) {
      bankTransactions.add(fromBankTransactionEntityToBankTransaction(bankTransactionEntity));
    }    
    return bankTransactions;
  }
  
  /**
   * Conversion BankTransactionEntity to BankTransaction
   * 
   * @param bankTransactionEntity BankTransactionEntity object
   * @return BankTransaction
   */
  public BankTransaction fromBankTransactionEntityToBankTransaction(BankTransactionEntity bankTransactionEntity) {
    BankTransaction bankTransaction = new BankTransaction();
    bankTransaction.setId(bankTransactionEntity.getId());
    bankTransaction.setIdDebit(bankTransactionEntity.getCustomerDebit().getId());
    bankTransaction.setIdCredit(bankTransactionEntity.getCustomerCredit().getId());
    bankTransaction.setConnection("");
    bankTransaction.setTransactionDate(bankTransactionEntity.getTransactionDate());
    bankTransaction.setDescription(bankTransactionEntity.getDescription());
    bankTransaction.setAmount(bankTransactionEntity.getAmount());
    bankTransaction.setLevy(bankTransactionEntity.getLevy());
    return bankTransaction;
  }
  
  /**
   * Conversion BankTransaction to BankTransactionEntity
   * 
   * @param bankTransaction BankTransaction object
   * @param customerDebit CustomerEntity debit object
   * @param customerCredit CustomerEntity credit object
   * @return BankTransactionEntity
   */
  public BankTransactionEntity fromBankTransactionToBankTransactionEntity(BankTransaction bankTransaction) {
    BankTransactionEntity bankTransactionEntity = new BankTransactionEntity();
    bankTransactionEntity.setId(bankTransaction.getId());
    bankTransactionEntity.setCustomerDebit(null);
    bankTransactionEntity.setCustomerCredit(null);
    bankTransactionEntity.setTransactionDate(bankTransaction.getTransactionDate());
    bankTransactionEntity.setDescription(bankTransaction.getDescription());
    bankTransactionEntity.setAmount(bankTransaction.getAmount());
    bankTransactionEntity.setLevy(bankTransaction.getLevy());
    return bankTransactionEntity;
  }
}
