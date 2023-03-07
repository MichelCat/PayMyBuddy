package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;

public interface BankTransactionEntityDao extends JpaRepository<BankTransactionEntity, Integer> {

}
