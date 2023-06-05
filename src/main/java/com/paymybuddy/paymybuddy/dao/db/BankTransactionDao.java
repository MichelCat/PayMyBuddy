package com.paymybuddy.paymybuddy.dao.db;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * BankTransactionDao is the interface that manages BankTransactionEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface BankTransactionDao extends JpaRepository<BankTransactionEntity, Integer> {
  
  Page<BankTransactionEntity> findByCustomerDebitOrCustomerCreditOrderByTransactionDateDesc(
                                            Optional<CustomerEntity> customerDebit
                                            , Optional<CustomerEntity> customerCredit
                                            , Pageable pageable);
  
  List<BankTransactionEntity> findFirst2ByCustomerDebitOrCustomerCreditOrderByTransactionDateDesc(
                                            Optional<CustomerEntity> customerDebit
                                            , Optional<CustomerEntity> customerCredit);
}
