package br.com.vr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = { "br.com.vr" })
@ComponentScan(basePackages = { "br.com.vr" })
public class RecrutamentoVrApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecrutamentoVrApplication.class, args);
	}

}
