package com.paymybuddy.paymybuddy.dao.db.entities;

import java.sql.Timestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * CustomerMessageEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "customer_message")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = {"customer", "messageDate", "subject", "message_detail"})
@ToString
public class CustomerMessageEntity {
  /**
   * Message ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_message")
  Integer id;
  /**
   * Message customer ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="message_id_customer", nullable=false)
  private CustomerEntity customer;
  /**
   * Message date
   */
  @NotNull(message = "Message date cannot be null")
  @PastOrPresent
  @Column(name = "message_date")
  Timestamp messageDate;
  /**
   * Subject
   */
  @NotBlank(message = "Subject is required")
  @Column(name = "message_subject")
  @Size(max = 100)
  String subject;
  /**
   * Detail
   */
  @NotBlank(message = "Detail is required")
  @Column(name = "message_detail")
  @Size(max = 500)
  String detail;
}
