package com.caboooom.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.caboooom.userservice.config.oauth.CustomOidcUserService;
import com.caboooom.userservice.config.oauth.GoogleOAuthLoginFailureHandler;
import com.caboooom.userservice.config.oauth.GoogleOAuthLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final GoogleOAuthLoginSuccessHandler googleOAuthLoginSuccessHandler;
    private final GoogleOAuthLoginFailureHandler googleOAuthLoginFailureHandler;
    private final CustomOidcUserService customOidcUserService;

    public SecurityConfig(GoogleOAuthLoginSuccessHandler googleOAuthLoginSuccessHandler,
                          GoogleOAuthLoginFailureHandler googleOAuthLoginFailureHandler,
                          CustomOidcUserService customOidcUserService) {
        this.googleOAuthLoginSuccessHandler = googleOAuthLoginSuccessHandler;
        this.googleOAuthLoginFailureHandler = googleOAuthLoginFailureHandler;
        this.customOidcUserService = customOidcUserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/oauth_state", "/login", "/signup", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("http://localhost:8080/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("http://localhost:8080/?state=loginSuccess", true)
                        .failureUrl("http://localhost:8080/login?state=error")
                        .permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("http://localhost:8080/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOidcUserService))
                        .authorizationEndpoint(authEndpoint -> authEndpoint
                                .baseUri("/oauth2/authorization"))
                        .redirectionEndpoint(redirect -> redirect
                                .baseUri("/asdf"))
                                .successHandler(googleOAuthLoginSuccessHandler)
                                .failureHandler(googleOAuthLoginFailureHandler)
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
