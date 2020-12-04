package br.com.bandtec.ac3fernanda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Configuration
public class Ac3FernandaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ac3FernandaApplication.class, args);
	}

}
