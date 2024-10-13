package com.lokidev.auth.security;

import com.lokidev.auth.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {
  // Generate a secure HS512 key
  private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRY_CONSTANT);
    return Jwts.builder()
        .setIssuedAt(currentDate)
        .setExpiration(expiryDate)
        .setSubject(username)
        .signWith(SignatureAlgorithm.HS512, key)
        .compact();
  }

  // Extract username from JWT token
  public String getUsernameFromJWT(String token) {
    Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  // Validate the JWT token
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(key).parseClaimsJws(token); // Ensure it's signed JWT
      return true;
    } catch (Exception ex) {
      throw new AuthenticationCredentialsNotFoundException("JWT expired or incorrect", ex);
    }
  }
}
