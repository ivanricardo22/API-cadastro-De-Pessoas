package com.examplo.meuprojeto.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
//@ComponentScan(basePackages = "com.examplo.meuprojeto")

public class MeuprojetoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeuprojetoApplication.class, args);
	}



}
