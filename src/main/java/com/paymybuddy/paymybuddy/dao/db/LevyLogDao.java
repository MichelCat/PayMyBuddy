package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.LevyLogEntity;

/**
 * LevyLogDao is the interface that manages LevyLogEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface LevyLogDao extends JpaRepository<LevyLogEntity, Integer> {

}
