package com.devs.ticketapp.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.devs.ticketapp.controller"))
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());
    }
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "TicketQueue API", 
	      "API para Sistema de colas, mitigando el problema de las aglomeraciones de personas ocasionadas al momento de realizar trámites en alguna entidad. Por medio de una solución de tickets electrónicos que puede ser adoptado por diferentes sectores que así lo requieran..", 
	      "v1", 
	      "Terms of service", 
	      new Contact("El Salvador Conectado", "https://elsalvadorconectado.org/", "contacto@elsalvadorconectado.org"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
}
