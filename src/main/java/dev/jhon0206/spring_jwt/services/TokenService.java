package dev.jhon0206.spring_jwt.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.jhon0206.spring_jwt.entities.Token;
import dev.jhon0206.spring_jwt.entities.Token.TokenType;
import dev.jhon0206.spring_jwt.entities.User;
import dev.jhon0206.spring_jwt.repositories.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
  private final TokenRepository repository;
  @Value("${application.jwt.token-expiration-after-days}")
  private Integer tokenExpirationAfterDays;
  @Value("${application.jwt.refresh-token-expiration-after-days}")
  private Integer refreshTokenExpirationAfterDays;

  public List<Token> getTokensByUser(User user) throws Exception{
    return repository.findByUser(user).orElseThrow(()->new Exception("User not found"));
  }

  @Transactional
  public void saveTokens(User user, String jwtToken, String refreshToken) {
    try {
      
    Token jwtTokenObj = Token.builder()
        .user(user)
        .token(jwtToken)
        .expirationDate(LocalDateTime.now().plusDays(tokenExpirationAfterDays))
        .type(TokenType.TOKEN)
        .build();
    Token refreshTokenObj = Token.builder()
        .user(user)
        .token(jwtToken)
        .expirationDate(LocalDateTime.now().plusDays(refreshTokenExpirationAfterDays))
        .type(TokenType.REFRESH_TOKEN)
        .build();
    repository.save(jwtTokenObj);
    repository.save(refreshTokenObj);
    } catch (Exception e) {
      throw new RuntimeException("Error saving tokens");
    }
  }
}
