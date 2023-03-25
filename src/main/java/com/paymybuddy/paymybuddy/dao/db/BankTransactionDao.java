package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;

/**
 * BankTransactionDao is the interface that manages BankTransactionEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface BankTransactionDao extends JpaRepository<BankTransactionEntity, Integer> {

}
