package com.lokidev.auth.models;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class Users {
  @Id // You should define an id for the entity
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id") // Example id column
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Roles> roles;
}
