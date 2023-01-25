package com.embedika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmbedikaApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmbedikaApplication.class, args);
	}
}
