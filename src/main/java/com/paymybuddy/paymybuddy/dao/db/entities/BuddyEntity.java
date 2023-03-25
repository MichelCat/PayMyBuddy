package com.paymybuddy.paymybuddy.dao.db.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * BuddyEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "buddy")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = {"personEntityUser", "personEntityBuddy"})
@ToString(callSuper = true)
public class BuddyEntity {
  /**
   * Buddy ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer idBuddy;
  /**
   * User ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="buddyIdUser", nullable=false)
  private PersonEntity personEntityUser;
  /**
   * Friend ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="buddyIdBuddy", nullable=false)
  private PersonEntity personEntityBuddy;
}
