package ru.jg.attractions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(c -> c.disable())
                .authorizeHttpRequests(c -> c.requestMatchers("/eureka/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(c -> c.jwt(Customizer.withDefaults()))
                .build();
    }
}
