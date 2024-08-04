package com.food_delivery_app.appuser.controller;

import com.food_delivery_app.appuser.payload.AppUserDto;
import com.food_delivery_app.appuser.payload.LoginDto;
import com.food_delivery_app.appuser.payload.TokenResponse;
import com.food_delivery_app.appuser.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/food-app/user")
public class AppUserController {

    private AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody AppUserDto appUserDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(Objects.requireNonNull(result.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
         AppUserDto appUserDto1 = appUserService.singUpUser(appUserDto);
        return new ResponseEntity<>(appUserDto1,HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signUpUser( @RequestBody LoginDto loginDto){
         String token = appUserService.signInUser(loginDto);
         if(token!=null){
              TokenResponse tokenResponse = new TokenResponse();
              tokenResponse.setType("Jwt Token");
              tokenResponse.setToken(token);
              return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
         }
        return new ResponseEntity<>("Invalid Password",HttpStatus.BAD_REQUEST);
    }
}
