package com.example.demo;

import com.example.demo.repository.ExerciseRepository;
import com.example.demo.service.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LiftTrackerApplication {

	@Autowired
	DataLoader dataLoader;

	public static void main(String[] args) {
		SpringApplication.run(LiftTrackerApplication.class, args);

		System.out.println("Loaded data");
	}

	@PostConstruct
	void seePosts() {
//		dataLoader.clearData();
		//dataLoader.loadData();
//		dataLoader.loadDataBiDirectional();
//		dataLoader.test();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
