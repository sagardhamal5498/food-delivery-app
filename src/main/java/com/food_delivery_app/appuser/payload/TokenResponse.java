package com.food_delivery_app.appuser.payload;

import lombok.Data;

@Data
public class TokenResponse {
    private String type;
    private String token;
}
