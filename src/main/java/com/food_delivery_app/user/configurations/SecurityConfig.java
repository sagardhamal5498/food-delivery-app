package com.food_delivery_app.user.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain sfc(HttpSecurity http) throws Exception{

        http.csrf().disable().cors().disable();

        http.authorizeHttpRequests().requestMatchers("/api/v1/user/add").permitAll()
                .requestMatchers("/api/v1/user/login").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(jwtFilter , AuthorizationFilter.class);

        return http.build();
    }
}
