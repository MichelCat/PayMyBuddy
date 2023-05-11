package com.paymybuddy.paymybuddy.dao.db;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.controller.utils.CustomerBuddyUtils;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerBuddyEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


/**
 * CustomerBuddyDaoImpl manages the CustomerBuddyEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public class CustomerBuddyDaoImpl implements CustomerBuddyDao {
  
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private CustomerBuddyUtils customerBuddyUtils;

  @Override
  public List<CustomerBuddyEntity> findCustomerBuddyById(Integer id) {
    var query = entityManager.createNativeQuery("select"
                                                  + " buddy.buddy_id_user as idUser"
                                                  + " , buddy.buddy_id_buddy as idBuddy"
                                                  + " , buddy.buddy_connection as connection"
                                                  + " , customer.customer_email as email"
                                                  + " from buddy"
                                                  + " inner join customer on customer.id_customer = buddy.id_buddy"
                                                  + " where buddy.buddy_id_user = :id");
    query.setParameter("id", id);
    List<Object[]> results = query.getResultList();
    return customerBuddyUtils.fromListObjectToListCustomerBuddyEntity(results);
  }
}
