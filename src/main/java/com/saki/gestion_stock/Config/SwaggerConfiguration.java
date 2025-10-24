// Déclaration du package pour la configuration Swagger
package com.saki.gestion_stock.Config;

// Importations des classes nécessaires pour Swagger
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// Déclaration de cette classe comme configuration Spring
@Configuration
// Activation de Swagger 2
@EnableSwagger2
public class SwaggerConfiguration {

    // Constante pour le nom du header d'autorisation
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Configuration principale du bean Docket pour Swagger
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                // Configuration des informations de l'API
                .apiInfo(
                        new ApiInfoBuilder()
                                .description("Gestion de stock API documentation")
                                .title("Gestion de stock REST API")
                                .build()
                )
                // Nom du groupe d'API
                .groupName("REST API V1")
                // Configuration du contexte de sécurité pour JWT
                .securityContexts(Collections.singletonList(securityContext()))
                // Configuration des schémas de sécurité
                .securitySchemes(Collections.singletonList(apiKey()))
                // Désactivation des messages de réponse par défaut
                .useDefaultResponseMessages(false)
                // Configuration de la sélection des endpoints
                .select()
                // Scan des contrôleurs dans le package spécifié
                .apis(RequestHandlerSelectors.basePackage("com.saki.gestion_stock"))
                // Inclusion de tous les paths
                .paths(PathSelectors.any())
                .build();
    }

    // Configuration de la clé API pour JWT
    private ApiKey apiKey() {
        // Création d'une clé API nommée "JWT" pour le header Authorization
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    // Configuration du contexte de sécurité
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                // Application de l'authentification par défaut
                .securityReferences(defaultAuth())
                .build();
    }

    // Configuration des références de sécurité par défaut
    List<SecurityReference> defaultAuth() {
        // Définition du scope d'autorisation (accès global)
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        // Création du tableau des scopes d'autorisation
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        // Retour de la référence de sécurité pour JWT
        return Collections.singletonList(
                new SecurityReference("JWT", authorizationScopes));
    }

}