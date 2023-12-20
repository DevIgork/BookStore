package com.example.bookstore.dto;

import com.example.bookstore.validate.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword", message = " do not match!")
public class UserRegistrationRequestDto {
    @Email
    private String email;
    @NotNull(message = " value can't be null")
    @Length(min = 8, max = 35)
    private String password;
    @NotNull(message = " value can't be null")
    @Length(min = 8, max = 35)
    private String repeatPassword;
    @NotNull(message = " value can't be null")
    private String firstName;
    @NotNull(message = " value can't be null")
    private String lastName;
    private String shipingAddress;
}
