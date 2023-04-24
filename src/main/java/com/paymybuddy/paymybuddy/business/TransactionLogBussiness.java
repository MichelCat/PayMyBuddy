package com.paymybuddy.paymybuddy.business;

import org.springframework.beans.factory.annotation.Autowired;
import com.paymybuddy.paymybuddy.dao.db.TransactionLogDao;

public class TransactionLogBussiness {

  @Autowired
  private TransactionLogDao transactionLogDao;

}
