package com.marsa.backendstageapplication;

import com.marsa.backendstageapplication.user.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class BackendStageApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendStageApplication.class, args);
	}

//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	CommandLineRunner run(UserService userService, RoleRepository roleRepository) {
		return args -> {
			userService.saveRole(new Role(1 ,"ADMIN"));
			userService.saveRole(new Role(2 ,"FORM_CREATOR"));
			userService.saveRole(new Role(3 , "FORM_READER"));

//			userService.createUser(new User("user", "heddiomar@gmail.com", "user", "Heddi", "Omar", "T1", new ArrayList<>()));

//			userService.createUser(new User("admin", "heddiomar@gmail.com", "admin", "Heddi", "Omar", "T1", new ArrayList<>()));
//
//			userService.addRoleToUser("admin", "ADMIN");
		};
	}

}
