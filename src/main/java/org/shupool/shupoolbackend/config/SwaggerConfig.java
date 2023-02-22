package org.shupool.shupoolbackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().title("ShuPool").version("1.0.0")
                .description("ShuPool 애플리케이션 API입니다.")
                .termsOfService("http://swagger.io/terms/");


        SecurityScheme basichAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("basic");
        SecurityRequirement securityItem = new SecurityRequirement().addList("basicAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicAuth", basichAuth))
                .addSecurityItem(securityItem)
                .info(info);
    }
}
