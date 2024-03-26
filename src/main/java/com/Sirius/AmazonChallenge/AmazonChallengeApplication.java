package com.Sirius.AmazonChallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AmazonChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonChallengeApplication.class, args);
	}

}
