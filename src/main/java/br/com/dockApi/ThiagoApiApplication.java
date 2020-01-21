package br.com.dockApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "br.com.dockApi" })
public class ThiagoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThiagoApiApplication.class, args);
	}

}
