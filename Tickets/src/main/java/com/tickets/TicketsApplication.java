package com.tickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class TicketsApplication {

	public static void main(String[] args) {

		SpringApplication.run(TicketsApplication.class, args);
		System.out.println("hi this is main class");
		new Test().test();
	}

}
