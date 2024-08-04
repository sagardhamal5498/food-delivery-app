package com.food_delivery_app.user.controller;

import com.food_delivery_app.user.entities.User;
import com.food_delivery_app.user.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDetails(@RequestBody User user){
        User userentity = userService.signup(user);
        return new ResponseEntity<>(userentity, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> verify(@RequestParam String username, @RequestParam String password){

        String result = userService.login(username, password);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
