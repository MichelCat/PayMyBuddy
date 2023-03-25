package com.paymybuddy.paymybuddy.business;

import org.springframework.beans.factory.annotation.Autowired;
import com.paymybuddy.paymybuddy.dao.db.BankTransactionDao;

public class BankTransactionBussiness {

  @Autowired
  private BankTransactionDao bankTransactionDao;

}
