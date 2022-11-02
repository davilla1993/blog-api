package com.gbossoufolly.blogapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKeys() {

        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityContext> securityContext() {

        return Arrays.asList(
                SecurityContext
                        .builder()
                        .securityReferences(securityReference())
                        .build()
        );
    }

    private List<SecurityReference> securityReference() {

        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");

        return Arrays.asList(new SecurityReference("scope", new AuthorizationScope[] { scope }));
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContext())
                .securitySchemes(Arrays.asList(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getInfo() {

        return new ApiInfo("Blog API",
                "This is complete blog API developed with Spring boot",
                "1.0",
                "Terms of Service",
                new Contact("Carlo GBOSSOU",
                        "https://gbossoufolly.com",
                        "carlogbossou93@gmail.com"),
                "License of API",
                "Terms of License",
                Collections.emptyList());
    }
}
