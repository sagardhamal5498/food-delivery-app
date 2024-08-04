package com.food_delivery_app.user.service;

import com.food_delivery_app.user.entities.User;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {

    public User signup(User user);

    public String login(String username, String password);
}
