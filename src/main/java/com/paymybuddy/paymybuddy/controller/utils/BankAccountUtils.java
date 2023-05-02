package com.paymybuddy.paymybuddy.controller.utils;

import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.BankAccount;
import com.paymybuddy.paymybuddy.dao.db.entities.BankAccountEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * BankAccountUtils is an BankAccount object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class BankAccountUtils {

  /**
   * Conversion BankAccountEntity to BankAccount
   * 
   * @param bankAccountEntity BankAccountEntity object
   * @return BankAccount
   */
  public BankAccount fromBankAccountEntityToBankAccount(BankAccountEntity bankAccountEntity) {
    BankAccount bankAccount = new BankAccount();
    bankAccount.setId(bankAccountEntity.getId());
    bankAccount.setIdCustomer(bankAccountEntity.getCustomer().getId());
    bankAccount.setBankName(bankAccountEntity.getBankName());
    bankAccount.setBankCode(bankAccountEntity.getBankCode());
    bankAccount.setBranchCode(bankAccountEntity.getBranchCode());
    bankAccount.setAccountNumber(bankAccountEntity.getAccountNumber());
    bankAccount.setRib(bankAccountEntity.getRib());
    bankAccount.setIban(bankAccountEntity.getIban());
    bankAccount.setBic(bankAccountEntity.getBic());
    return bankAccount;
  }
  
  public BankAccountEntity fromBankAccountToBankAccountEntity(BankAccount bankAccount
                        , CustomerEntity custome) {
    BankAccountEntity bankAccountEntity = new BankAccountEntity();
    bankAccountEntity.setId(bankAccount.getId());
    bankAccountEntity.setCustomer(custome);
    bankAccountEntity.setBankName(bankAccount.getBankName());
    bankAccountEntity.setBankCode(bankAccount.getBankCode());
    bankAccountEntity.setBranchCode(bankAccount.getBranchCode());
    bankAccountEntity.setAccountNumber(bankAccount.getAccountNumber());
    bankAccountEntity.setRib(bankAccount.getRib());
    bankAccountEntity.setIban(bankAccount.getIban());
    bankAccountEntity.setBic(bankAccount.getBic());
    return bankAccountEntity;
  }
  
  
}
