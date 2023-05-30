package com.paymybuddy.paymybuddy.dao.db;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
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

  @Override
  public Page<CustomerBuddyEntity> findCustomerBuddyById(Integer id, Pageable pageable) {
    var query = entityManager.createNativeQuery("select"
                                                  + " buddy.buddy_id_user as idUser"
                                                  + " , buddy.buddy_id_buddy as idBuddy"
                                                  + " , buddy.buddy_connection as connection"
                                                  + " , user_email as email"
                                                  + " from buddy"
                                                  + " inner join customer on customer.id_customer = buddy.buddy_id_buddy"
                                                  + " inner join app_user on app_user.id_user = customer.customer_id_user"
                                                  + " where buddy.buddy_id_user = :id");
    query.setFirstResult((int)pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());    
    query.setParameter("id", id);
    
    List<Object[]> results = query.getResultList();
    
    List<CustomerBuddyEntity> customerBuddyEntities = new ArrayList<>();
    for (Object[] object : results) {
      CustomerBuddyEntity customerBuddyEntity = new CustomerBuddyEntity();
      customerBuddyEntity.setIdUser((Integer)object[0]);
      customerBuddyEntity.setIdBuddy((Integer)object[1]);
      customerBuddyEntity.setConnection((String)object[2]);
      customerBuddyEntity.setEmail((String)object[3]);
      customerBuddyEntities.add(customerBuddyEntity);
    }
    
    // Number of pages
    var queryTotalRows = entityManager.createNativeQuery("select count(*)"
                                                        + " from buddy"
                                                        + " inner join customer on customer.id_customer = buddy.buddy_id_buddy"
                                                        + " inner join app_user on app_user.id_user = customer.customer_id_user"
                                                        + " where buddy.buddy_id_user = :id");
    queryTotalRows.setParameter("id", id);
    Long totalRows = (Long)queryTotalRows.getSingleResult();
    
    return new PageImpl<>(customerBuddyEntities, pageable, totalRows);
  }
}
