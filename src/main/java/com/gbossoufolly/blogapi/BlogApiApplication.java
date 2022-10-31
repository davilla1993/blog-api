package com.gbossoufolly.blogapi;

import com.gbossoufolly.blogapi.config.AppConstants;
import com.gbossoufolly.blogapi.entities.Role;
import com.gbossoufolly.blogapi.entities.User;
import com.gbossoufolly.blogapi.payloads.UserDTO;
import com.gbossoufolly.blogapi.repositories.RoleRepository;
import com.gbossoufolly.blogapi.repositories.UserRepository;
import com.gbossoufolly.blogapi.services.UserService;
import com.gbossoufolly.blogapi.services.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {

		SpringApplication.run(BlogApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(passwordEncoder.encode("1234"));

		try{

			Role role_user = new Role();
			role_user.setId(AppConstants.ROLE_USER);
			role_user.setName("ROLE_USER");

			Role role_admin = new Role();
			role_admin.setId(AppConstants.ROLE_ADMIN);
			role_admin.setName("ROLE_ADMIN");

			List<Role> roles = List.of(role_admin, role_user);

			List<Role> result = roleRepository.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());

			});

		}catch (Exception ex) {
			ex.getMessage();
		}

	}
}
