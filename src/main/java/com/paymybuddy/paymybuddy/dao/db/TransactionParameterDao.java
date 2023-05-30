package com.paymybuddy.paymybuddy.dao.db;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionParameterEntity;

/**
 * TransactionParameterDao is the interface that manages TransactionParameterEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface TransactionParameterDao extends JpaRepository<TransactionParameterEntity, Integer> {

  public Optional<TransactionParameterEntity> findFirstByOrderByEffectiveDateDesc();
  
  public Page<TransactionParameterEntity> findAllByOrderByEffectiveDateDesc(Pageable pageable);
}
