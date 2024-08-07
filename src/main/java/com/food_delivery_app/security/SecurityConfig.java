package com.food_delivery_app.security;

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
                 .requestMatchers("/api/v1/food-app/user/signUp","/api/v1/food-app/user/signIn","/api/v1/food-app/ratings/add",
                         "/api/v1/food-app/ratings/delete","/api/v1/food-app/ratings/getall").permitAll()
                 .requestMatchers("/api/v1/food-app/restaurant/add","/api/v1/food-app/restaurant/remove"
                         ,"/api/v1/food-app/restaurant/update/{restaurantId}"
                         ,"/api/v1/food-app/menu/add","/api/v1/food-app/menu/search/{restaurantId}",
                         "/api/v1/food-app/restaurant/search","/api/v1/food-app/order/make/{restaurantId}"
                 ,"/api/v1/food-app/order/delete/{orderId}","/api/v1/food-app/menu/update",
                         "/api/v1/food-app/delivery-executive/add","/api/v1/food-app/delivery-executive/delete/{agentId}"
                 ,"/api/v1/food-app/delivery-executive/update/{agentId}","/api/v1/food-app/delivery-executive/{agentId}/orders").hasAnyRole("USER","ADMIN")
                 .anyRequest()
                 .authenticated();
         return http.build();
    }


}
