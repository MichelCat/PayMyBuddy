package com.paymybuddy.paymybuddy.dao.db;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerMessageEntity;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;

/**
 * CustomerMessageDao is the interface that manages CustomerMessageEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface CustomerMessageDao extends JpaRepository<CustomerMessageEntity, Integer> {

  Page<CustomerMessageEntity> findByAppUserEntitySenderOrAppUserEntityRecipientOrderByMessageDateDesc(
                                        Optional<AppUserEntity> idSender
                                        , Optional<AppUserEntity> idRecipient
                                        , Pageable pageable);
}
