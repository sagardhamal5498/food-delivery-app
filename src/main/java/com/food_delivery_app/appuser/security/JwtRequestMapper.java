package com.food_delivery_app.appuser.security;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.appuser.repository.AppUserRepository;
import com.food_delivery_app.appuser.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;


@Configuration
public class JwtRequestMapper  extends OncePerRequestFilter {
    private JwtService jwtService;
    private AppUserRepository appUserRepository;

    public JwtRequestMapper(JwtService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

         String tokenHeader = request.getHeader("Authorization");
         if(tokenHeader!=null&&tokenHeader.startsWith("Bearer ")){
              String token = tokenHeader.substring(8, tokenHeader.length() - 1);
              String userName = jwtService.getUserName(token);
              AppUser appUser = appUserRepository.findByUsername(userName).get();
              UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                     appUser,
                     null,
                     Collections.singleton(new SimpleGrantedAuthority(appUser.getRole()))
             );
              authToken.setDetails(new WebAuthenticationDetails(request));
             SecurityContextHolder.getContext().setAuthentication(authToken);
         }


         filterChain.doFilter(request,response);

    }
}
