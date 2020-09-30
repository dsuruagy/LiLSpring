package com.dsuruagy.lil.learningspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearningSpringApplication {
	private static Logger logger = 
		LoggerFactory.getLogger(LearningSpringApplication.class.getName());

	public static void main(String[] args) {
		logger.info("ENCODING: " + System.getProperty("file.encoding"));
		SpringApplication.run(LearningSpringApplication.class, args);
	}
}
