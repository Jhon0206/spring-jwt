package dev.jhon0206.spring_jwt.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.jhon0206.spring_jwt.entities.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
  @Query("SELECT t FROM Token t INNER JOIN User u ON t.user.id = u.id WHERE u.id = :id AND (t.isEnabled=true OR t.isExpired=true)")
  Optional<List<Token>> validTokensfindByUser(Integer id);

  Optional<Token> findByToken(String token);
}
