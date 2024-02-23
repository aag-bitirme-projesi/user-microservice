package com.hacettepe.usermicroservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI()
    {
        Server devServer = new Server();
        devServer.setUrl("https://localhost:8080");
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl("https://localhost:8080");
        prodServer.setDescription("Server URL in Production environment");

        Info info = new Info()
                .title("Todo website API")
                .version("1.0")
                .description("This API exposes endpoints to manage demo.");

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
