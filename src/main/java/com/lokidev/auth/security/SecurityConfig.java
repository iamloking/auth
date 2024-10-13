package com.lokidev.auth.security;

import com.lokidev.auth.service.CustomUsersDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// Learn more about this
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final CustomUsersDetailsService usersDetailsService;
  private final JWTAuthEntryPoint jwtAuthEntryPoint;
  private final JWTAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for stateless API
        .exceptionHandling(
            (exceptionHandlingConfigurer) ->
                exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthEntryPoint))
        .sessionManagement(
            session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy
                        .STATELESS) // Stateless session (suitable for JWT or token-based auth)
            )
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/auth/**")
                    .permitAll() // Allow access to specific public API endpoints
                    .anyRequest()
                    .authenticated() // Require authentication for all other endpoints
            );

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build(); // Build and return the SecurityFilterChain
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
