package com.gbossoufolly.blogapi;

import com.gbossoufolly.blogapi.entities.User;
import com.gbossoufolly.blogapi.payloads.UserDTO;
import com.gbossoufolly.blogapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class BlogApiApplication {


	public static void main(String[] args) {

		SpringApplication.run(BlogApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

}
