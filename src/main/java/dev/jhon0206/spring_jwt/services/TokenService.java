package dev.jhon0206.spring_jwt.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.jhon0206.spring_jwt.entities.Token;
import dev.jhon0206.spring_jwt.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService{
  private final TokenRepository repository;

  private List<Token> getValidTokensbyUser(Integer id){
    return repository.validTokensfindByUser(id).orElse(new ArrayList<Token>());
  }
}
