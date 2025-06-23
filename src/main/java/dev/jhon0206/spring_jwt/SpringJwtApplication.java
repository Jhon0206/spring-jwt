package dev.jhon0206.spring_jwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.jhon0206.spring_jwt.entities.User;
import dev.jhon0206.spring_jwt.repositories.UserRepository;
import dev.jhon0206.spring_jwt.util.RolesEnum;

@SpringBootApplication
public class SpringJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJwtApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			// Opcional: Crear un usuario por defecto para pruebas
			if (userRepository.findByEmail("admin@example.com").isEmpty()) {
				var user = User.builder()
						.name("Admin")
						.lastName("User")
						.email("admin@example.com")
						.pass(passwordEncoder.encode("password"))
						.role(RolesEnum.ADMIN)
						.build();
				userRepository.save(user);
			}
		};
	}

}
