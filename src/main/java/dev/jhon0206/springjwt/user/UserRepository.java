package dev.jhon0206.springjwt.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer>{
  Optional<User>  findByEmail(String email);
  List<User> findByStatus(StatusEnum status);
}
