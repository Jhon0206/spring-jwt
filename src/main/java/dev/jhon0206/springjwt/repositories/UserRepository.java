package dev.jhon0206.springjwt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.jhon0206.springjwt.entities.User;


public interface UserRepository extends JpaRepository<User,Integer>{
  Optional<User>  findByEmail(String email);
}
