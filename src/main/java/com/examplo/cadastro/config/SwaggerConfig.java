package com.examplo.cadastro.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Cadastro")
                        .description("Sistema de cadastro desenvolvido com Spring Boot")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ivan Ricardo")
                                .email("yvanryc@hotmail.com")
                                .url("https://github.com/ivanricardo22/API-cadastro-De-Pessoas")));
    }

}
