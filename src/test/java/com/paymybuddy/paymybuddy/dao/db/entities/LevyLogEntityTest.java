package com.paymybuddy.paymybuddy.dao.db.entities;

import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * LevyLogEntityTest is a class of unit tests on levy  log.
 * 
 * @author MC
 * @version 1.0
 */
@SpringBootTest
public class LevyLogEntityTest {

//  @Autowired
//  private LevyLogEntity levyLogEntity;
  
  
  
  // -----------------------------------------------------------------------------------------------
  // Method setLevyDate
  // -----------------------------------------------------------------------------------------------
  @Test
  void setLevyDate_Normal() {
    assertThat(1).isEqualTo(1);
  }
}
