package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.BankAccountEntity;

public interface BankAccountEntityDao extends JpaRepository<BankAccountEntity, Integer> {

}
