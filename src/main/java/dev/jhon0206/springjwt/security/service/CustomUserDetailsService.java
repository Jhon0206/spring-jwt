package dev.jhon0206.springjwt.security.service;

import dev.jhon0206.springjwt.security.model.CustomUserDetails;
import dev.jhon0206.springjwt.user.User;
import dev.jhon0206.springjwt.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: %s".formatted(username)));
    return new CustomUserDetails(user);
  }
}