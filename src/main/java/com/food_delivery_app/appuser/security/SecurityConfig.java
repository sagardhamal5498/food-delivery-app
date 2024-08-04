package com.food_delivery_app.appuser.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private JwtRequestMapper jwtRequestMapper;

    public SecurityConfig(JwtRequestMapper jwtRequestMapper) {
        this.jwtRequestMapper = jwtRequestMapper;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain (
            HttpSecurity http
    ) throws Exception {

        http.addFilterBefore(jwtRequestMapper, UsernamePasswordAuthenticationFilter.class);
         http.csrf().disable().cors().disable();
         http.authorizeHttpRequests()
                 .requestMatchers("/api/v1/food-app/user/signUp","/api/v1/food-app/user/signIn")
                 .permitAll();
         return http.build();
    }


}
