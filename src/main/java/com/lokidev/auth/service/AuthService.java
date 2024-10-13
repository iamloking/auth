package com.lokidev.auth.service;

import com.lokidev.auth.dtos.LoginRequest;
import com.lokidev.auth.dtos.RegisterRequest;
import com.lokidev.auth.models.Roles;
import com.lokidev.auth.models.Users;
import com.lokidev.auth.repository.RolesRepository;
import com.lokidev.auth.repository.UsersRepository;
import com.lokidev.auth.security.JWTGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.management.relation.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UsersRepository usersRepository;
  private final RolesRepository rolesRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JWTGenerator jwtGenerator;

  public String register(RegisterRequest request) throws RoleNotFoundException {
    if (usersRepository.existsByUsername(request.username())) {
      throw new BadCredentialsException("Username Already Exists");
    }
    Users user = new Users();
    user.setUsername(request.username());
    user.setPassword(passwordEncoder.encode(request.password()));
    List<Roles> roles = new ArrayList<>();
    Roles userRole = rolesRepository.findByName("user").get();
    roles.add(
        rolesRepository
            .findByName("user")
            .orElseThrow(() -> new RoleNotFoundException("Invalid Role")));
    user.setRoles(roles);
    usersRepository.save(user);
    return "Registration Successful";
  }

  public String login(LoginRequest request) {
    if (!usersRepository.existsByUsername(request.username())) {
      throw new BadCredentialsException("Bad Credentials");
    }
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtGenerator.generateToken(authentication);
  }
}
