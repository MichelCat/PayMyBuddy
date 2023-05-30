package com.paymybuddy.paymybuddy.dao.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerUserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * CustomerUserDaoImpl manages the CustomerUserEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public class CustomerUserDaoImpl implements CustomerUserDao {
  
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Page<CustomerUserEntity> findAllCustomerUser(Pageable pageable) {
    // Paginated entities
    var query = entityManager.createNativeQuery("select app_user.*, customer_first_name, customer_last_name"
                                                + " from customer"
                                                + " inner join app_user on id_user = customer_id_user"
                                                + " order by customer_last_name, customer_first_name");
    query.setFirstResult((int)pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());    
    
    List<Object[]> results = query.getResultList();
    
    List<CustomerUserEntity> customerUserEntities = new ArrayList<>();
    for (Object[] object : results) {
      CustomerUserEntity customerUserEntity = new CustomerUserEntity();
      customerUserEntity.setIdUser((Integer)object[0]);
      customerUserEntity.setUsername((String)object[1]);
      customerUserEntity.setPassword((String)object[2]);
      customerUserEntity.setAppUserRole((String)object[3]);
      customerUserEntity.setExpired((Boolean)object[4]);
      customerUserEntity.setLocked((Boolean)object[5]);
      customerUserEntity.setCredentiaExpired((Boolean)object[6]);
      customerUserEntity.setEnabled((Boolean)object[7]);
      customerUserEntity.setEmailValidationKey((String)object[8]);
      customerUserEntity.setFirstName((String)object[10]);
      customerUserEntity.setLastName((String)object[11]);
      
      try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = (String) object[9];
        Date date = dateFormat.parse(dateString);
        customerUserEntity.setValidEmailEndDate(date);
      } catch (Exception e) {
        customerUserEntity.setValidEmailEndDate(null);
      }
      customerUserEntities.add(customerUserEntity);
    }
    
    // Number of pages
    var queryTotalRows = entityManager.createNativeQuery("select count(*)"
                                                      + " from customer"
                                                      + " inner join app_user on id_user = id_customer");
    Long totalRows = (Long)queryTotalRows.getSingleResult();
    
    return new PageImpl<>(customerUserEntities, pageable, totalRows);
  }
}

