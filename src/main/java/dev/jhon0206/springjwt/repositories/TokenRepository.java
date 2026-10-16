package dev.jhon0206.springjwt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.jhon0206.springjwt.entities.Token;
import dev.jhon0206.springjwt.entities.User;

import java.util.List;


public interface TokenRepository extends JpaRepository<Token, Integer> {

  Optional<Token> findByToken(String token);

  Optional<List<Token>> findByUser(User user);
}
