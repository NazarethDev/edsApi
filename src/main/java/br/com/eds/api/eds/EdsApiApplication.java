package br.com.eds.api.eds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EdsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdsApiApplication.class, args);
	}

}
