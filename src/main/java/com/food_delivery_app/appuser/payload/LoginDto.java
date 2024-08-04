package com.food_delivery_app.appuser.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
