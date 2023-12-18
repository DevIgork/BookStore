package com.example.bookstore.dto;

import com.example.bookstore.validate.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(field = "password", fieldMatch = "verifyPassword", message = "Passwords do not match!")
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @NotNull
    @Length(min = 8, max = 35)
    private String password;
    @NotBlank
    @NotNull
    @Length(min = 8, max = 35)
    private String repeatPassword;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    private String shipingAddress;
}
