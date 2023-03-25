package com.paymybuddy.paymybuddy.business;

import org.springframework.beans.factory.annotation.Autowired;
import com.paymybuddy.paymybuddy.dao.db.BankAccountDao;

public class BankAccountBussiness {

  @Autowired
  private BankAccountDao bankAccountDao;

}
