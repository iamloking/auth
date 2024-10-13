package com.lokidev.auth.repository;

import com.lokidev.auth.models.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
  boolean existsByUsername(String username);

  Optional<Users> findByUsername(String username);
}
