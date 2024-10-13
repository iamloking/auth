package com.lokidev.auth.service;

import com.lokidev.auth.models.Roles;
import com.lokidev.auth.models.Users;
import com.lokidev.auth.repository.RolesRepository;
import com.lokidev.auth.repository.UsersRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService {
  private final UsersRepository usersRepository;
  private final RolesRepository rolesRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user =
        usersRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Roles> roles) {
    return roles.stream()
        .map((role) -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }
}
