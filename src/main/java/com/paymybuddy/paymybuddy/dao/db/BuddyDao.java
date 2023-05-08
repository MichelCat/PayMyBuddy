package com.paymybuddy.paymybuddy.dao.db;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * BuddyDao is the interface that manages BuddyEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface BuddyDao extends JpaRepository<BuddyEntity, Integer> {

  public List<BuddyEntity> findByCustomerUser(Optional<CustomerEntity> customerEntity);
  
  public BuddyEntity findByCustomerUserAndCustomerBuddy(Optional<CustomerEntity> customerUser
                                                      , Optional<CustomerEntity> customerBuddy);
  
//  @Query(value = "select"
//                + " buddy.id_buddy as id"
//                + " , buddy.buddy_id_user as idUser"
//                + " , buddy.buddy_id_buddy as idBuddy"
//                + " , customer.customer_email as email"
//                + " , buddy.buddy_connection as connection"
//                + " from buddy"
//                + " inner join customer on customer.id_customer = buddy.id_buddy"
//                + " where buddy.buddy_id_user = :id"
//  , nativeQuery = true)
//  List<Buddy> findBuddyById(@Param("id") Integer id);
  
}
