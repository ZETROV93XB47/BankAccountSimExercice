package com.example.bank.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonDeserializationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Désactiver l'acceptation des propriétés inconnues
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        // Limiter l'inclusion des propriétés nulles (facultatif, améliore la sécurité et les performances)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Désactiver la polymorphie par défaut (prévention des attaques par désérialisation)
        objectMapper.deactivateDefaultTyping();

        // Configurer la gestion des dates pour les formats modernes (Java 8 Time API)
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // (Optionnel) Configurer des features de sécurité supplémentaires si nécessaire
        // Ex: désactiver la gestion des types vides
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        return objectMapper;
    }
}
