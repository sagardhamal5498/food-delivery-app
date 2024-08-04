package com.food_delivery_app.appuser.service;

import com.food_delivery_app.appuser.payload.AppUserDto;
import com.food_delivery_app.appuser.payload.LoginDto;

public interface AppUserService {
    AppUserDto singUpUser(AppUserDto appUserDto);

    String signInUser(LoginDto loginDto);
}
