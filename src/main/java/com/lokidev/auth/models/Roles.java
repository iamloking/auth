package com.lokidev.auth.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Roles {
  @Id // You should define an id for the entity
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id") // Example id column
  private Long id;

  @Column(name = "name")
  private String name;
}
