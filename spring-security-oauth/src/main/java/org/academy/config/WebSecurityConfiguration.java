package org.academy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(HttpMethod.GET, "/").permitAll()
                .requestMatchers(HttpMethod.POST, "/promote").permitAll()
                .anyRequest().authenticated());
        http.oauth2Login(Customizer.withDefaults());
        http.logout(Customizer.withDefaults());
        http.headers(Customizer.withDefaults());
        http.anonymous(Customizer.withDefaults());
        return http.build();
    }

//    OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler() {
//        OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//        successHandler.setPostLogoutRedirectUri("http://localhost:8080/");
//        return successHandler;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/", "/login", "/error", "/webjars/**").permitAll() // Разрешаем доступ к этим маршрутам всем
//                        .requestMatchers("/h2-console/*").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // Только для администраторов
//                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
//                )
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Обработка ошибок аутентификации
//                )
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .loginPage("/") // Указываем страницу для входа
//                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
//                                .userService(socialAppService))
//                        .defaultSuccessUrl("/user")
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // URL для выхода
//                        .logoutSuccessHandler(oidcLogoutSuccessHandler()) // Обработчик для логаута
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                );
//        return http.build();
//    }
//
//    @Bean
//    public OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
//        OidcClientInitiatedLogoutSuccessHandler successHandler =
//                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//        successHandler.setPostLogoutRedirectUri("http://localhost:8080/");
//        return successHandler;
//    }

//
//    @Bean
//    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return (authorities) -> {
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//            authorities.forEach(authority -> {
//                //TODO Map roles
////				if (authority instanceof OidcUserAuthority){
////					OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
////					JSONArray keycloakRoles = (JSONArray) oidcUserAuthority.getAttributes().get("roles");
////					keycloakRoles.forEach(role -> mappedAuthorities.add(new SimpleGrantedAuthority((String) role)));
////				} else {
////					mappedAuthorities.add(authority);
////				}
//            });
//            return mappedAuthorities;
//        };
//    }
}