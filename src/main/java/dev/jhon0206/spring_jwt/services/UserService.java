package dev.jhon0206.spring_jwt.services;

import org.springframework.stereotype.Service;

import dev.jhon0206.spring_jwt.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService{
  private final UserRepository repository;

  
}
