package com.food_delivery_app.delivery_executive.payload;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeliveryExecutiveDto {

    private Long id;


    @Size(min =3,message = "name must contain at least 3 characters")
    private String name;


    @NotBlank(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Mobile number must be a valid number and 10-15 digits long")
    private String mobile;


    @NotBlank(message = "Address cannot be empty")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;
}
