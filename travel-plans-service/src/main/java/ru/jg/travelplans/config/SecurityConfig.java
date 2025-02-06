package ru.jg.travelplans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(c -> c.requestMatchers("/eureka/**").permitAll()
                .anyRequest().authenticated());
        //Использование метода  oauth2ResourceServer делает это приложение сервером ресурсов, потому к нему обращаются за ресурсами другие микросервисы
        http.oauth2ResourceServer(c  -> c.jwt(Customizer.withDefaults()));

        return http.build();
    }

//    OAuth2AuthorizedClientManager управляет процессом авторизации клиента и получением токенов

//    ClientRegistrationRepository:
//    Хранит информацию о клиенте, такую как client-id, client-secret, token-uri и другие параметры, необходимые для общения
//    с OAuth2 провайдером

//    OAuth2AuthorizedClientRepository:
//    Хранит информацию об авторизованных клиентах. В случае успешной авторизации он будет хранить токен доступа для клиента,
//    чтобы в будущем его можно было повторно использовать без необходимости повторной авторизации.

//    OAuth2AuthorizedClientManager:
//    Менеджер, который управляет процессом авторизации. Он берет на себя работу по получению, сохранению и повторному
//    использованию токенов доступа для OAuth2 клиентов.
//    Использует OAuth2AuthorizedClientProvider для запроса токенов и OAuth2AuthorizedClientRepository для хранения полученных токенов.

//    OAuth2AuthorizedClientProvider:
//    Это компонент, который отвечает за процесс авторизации и получение токенов доступа. В моем случае используется clientCredentials(),
//    что указывает на использование client credentials grant type.
    @Bean
    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository) {

        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        DefaultOAuth2AuthorizedClientManager cm = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                auth2AuthorizedClientRepository);

        cm.setAuthorizedClientProvider(provider);

        return cm;
    }
}
