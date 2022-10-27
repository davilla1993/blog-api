package com.gbossoufolly.blogapi;

import com.gbossoufolly.blogapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApiApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void repositoryTest() {

		String className = userRepository.getClass().getName();
		String packName = userRepository.getClass().getPackageName();

		System.out.println(className);
		System.out.println(packName);

	}

}
