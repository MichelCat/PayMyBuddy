package com.paymybuddy.paymybuddy.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.dao.db.UserAccountDao;

@Service
public class UserAccountBusiness {
  
  @Autowired
  private UserAccountDao userAccountDao;


}
