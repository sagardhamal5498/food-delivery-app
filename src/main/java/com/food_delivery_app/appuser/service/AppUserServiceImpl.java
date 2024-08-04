package com.food_delivery_app.appuser.service;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.appuser.exception.UserAlreadyExistsException;
import com.food_delivery_app.appuser.payload.AppUserDto;
import com.food_delivery_app.appuser.payload.LoginDto;
import com.food_delivery_app.appuser.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl  implements AppUserService{

    private PasswordEncoder passwordEncoder;
    private AppUserRepository appUserRepository;
    private JwtService jwtService;

    public AppUserServiceImpl(PasswordEncoder passwordEncoder, AppUserRepository appUserRepository, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AppUserDto singUpUser(AppUserDto appUserDto) {
       if(appUserRepository.findByUsername(appUserDto.getUsername()).isPresent()){
           throw new UserAlreadyExistsException("User already Exists");
       }
        AppUser appUser = AppUserDtoToEntity(appUserDto);
         AppUser save = appUserRepository.save(appUser);
        return AppUserEntityToDto(save);
    }

    @Override
    public String signInUser(LoginDto loginDto) {
         AppUser appUser = appUserRepository.findByUsername(loginDto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("user not found")
        );
        if(BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword())){
            return jwtService.generateToken(appUser);
        }
        return null;
    }

    private AppUserDto AppUserEntityToDto(AppUser appUser) {
        AppUserDto user = new AppUserDto();
        user.setUsername(appUser.getUsername());
        user.setMobile(appUser.getMobile());
        user.setRole(appUser.getRole());
        user.setAddress(appUser.getAddress());
        user.setEmail(appUser.getEmail());
        return user;
    }

    private AppUser AppUserDtoToEntity(AppUserDto dto) {
        AppUser user = new AppUser();
        user.setUsername(dto.getUsername());
        user.setAddress(dto.getAddress());
        user.setMobile(dto.getMobile());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        return user;
    }
}
