package ua.opnu.labwork2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Система управління бібліотекою")
                        .version("1.0.0")
                        .description("REST API для управління бібліотекою. Реалізовано функціонал управління авторами, книгами, читачами, категоріями та процесом видачі/повернення книг. Задокументовано згідно зі специфікацією OpenAPI 3.")
                        .contact(new Contact()
                                .name("Кафедра інформаційних систем")
                                .email("veraalbul12@gmail.com")));
    }
}