package dev.jhon0206.spring_jwt.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String token;
  @Column(name = "refresh_token", nullable = false)
  private String refreshToken;
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private TokenType type = TokenType.BEARER;
  @Column(nullable = false)
  private Boolean isEnabled;
  @Column(nullable = false)
  private Boolean isExpired;
  @Column(name = "date_creation", nullable = false)
  private LocalDateTime creationDate;  
  @Column(name = "date_expiration", nullable = false)
  private LocalDateTime expirationDate;
  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  public enum TokenType {
    BEARER
  }
}