package com.food_delivery_app.user.service;

import com.food_delivery_app.user.entities.User;
import com.food_delivery_app.user.exceptions.InvalidDetailsException;
import com.food_delivery_app.user.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    @Override
    public User signup(User user) {

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public String login(String username, String password) {

        Optional<User> opt = userRepository.findByUsername(username);
        if(opt.isPresent()){
            User user = opt.get();

            if(BCrypt.checkpw(password, user.getPassword())){

                String token = jwtService.generateToken(username);
                return token;
            }

        }
        else {
            throw new InvalidDetailsException("Username/Password is incorrect!!");
        }
        return null;

    }
}
