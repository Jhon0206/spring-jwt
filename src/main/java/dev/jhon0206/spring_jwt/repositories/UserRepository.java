package dev.jhon0206.spring_jwt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.jhon0206.spring_jwt.entities.User;


public interface UserRepository extends JpaRepository<User,Integer>{
  Optional<User>  findByEmail(String email);
}
