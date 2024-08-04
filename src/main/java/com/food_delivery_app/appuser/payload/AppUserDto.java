package com.food_delivery_app.appuser.payload;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppUserDto {

    @NotBlank(message = "Address cannot be empty")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Mobile number must be a valid number and 10-15 digits long")
    private String mobile;

    @NotBlank(message = "Role cannot be empty")
    @Pattern(regexp = "^(ROLE_ADMIN|ROLE_USER|ROLE_OWNER)$", message = "Role must be either ADMIN, USER, or OWNER")
    private String role;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String username;


}
