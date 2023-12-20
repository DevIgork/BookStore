package com.example.bookstore.dto;

import com.example.bookstore.validate.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword", message = " do not match!")
public class UserRegistrationRequestDto {
    @NotNull(message = " value can't be null")
    @Length(max = 255,message = "value max size is 255")
    @Email
    private String email;
    @NotNull(message = " value can't be null")
    @Length(min = 8, max = 35)
    private String password;
    @NotNull(message = " value can't be null")
    @Length(min = 8, max = 35)
    private String repeatPassword;
    @NotNull(message = " value can't be null")
    @Length(max = 255,message = "value max size is 255")
    private String firstName;
    @NotNull(message = " value can't be null")
    @Length(max = 255,message = "value max size is 255")
    private String lastName;
    @Length(max = 255,message = "value max size is 255")
    private String shipingAddress;
}
