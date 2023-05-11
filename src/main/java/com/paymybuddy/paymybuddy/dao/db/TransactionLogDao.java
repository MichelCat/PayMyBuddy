package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionLogEntity;

/**
 * TransactionLogDao is the interface that manages TransactionLogEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface TransactionLogDao extends JpaRepository<TransactionLogEntity, Integer> {

}
