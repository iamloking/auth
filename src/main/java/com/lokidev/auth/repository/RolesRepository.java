package com.lokidev.auth.repository;

import com.lokidev.auth.models.Roles;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
  Optional<Roles> findByName(String Name);
}
