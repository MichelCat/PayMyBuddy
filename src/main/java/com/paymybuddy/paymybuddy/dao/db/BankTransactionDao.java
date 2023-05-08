package com.paymybuddy.paymybuddy.dao.db;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * BankTransactionDao is the interface that manages BankTransactionEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface BankTransactionDao extends JpaRepository<BankTransactionEntity, Integer> {
  
  @Query(value = "select * from bank_transaction"
                + " where transaction_id_debit = :id or transaction_id_credit = :id"
                + " order by transaction_date desc"
      , nativeQuery = true)
  Page<BankTransactionEntity> findByIdNative(@Param("id") Integer id, Pageable pageable);
  
  public BankTransactionEntity findFirstByCustomerDebitOrCustomerCreditOrderByTransactionDateDesc(CustomerEntity customerDebit, CustomerEntity customerCredit);
  
}
