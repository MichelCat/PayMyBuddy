package com.paymybuddy.paymybuddy.dao.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerMessageEntity;

/**
 * CustomerMessageDao is the interface that manages CustomerMessageEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface CustomerMessageDao extends JpaRepository<CustomerMessageEntity, Integer> {

  @Query(value = "select * from customer_message"
      + " where message_id_sender = :id or message_id_recipient = :id"
      + " order by message_date desc"
  , nativeQuery = true)
  List<CustomerMessageEntity> getCustomerMessageById(@Param("id") Long id);
  
}
