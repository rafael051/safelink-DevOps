package br.com.fiap.safelink;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(
        title = "Safelink API",
        version = "v1",
        description = "API do sistema Safelink"
))
public class SafelinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafelinkApplication.class, args);
    }

}
