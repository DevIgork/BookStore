package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.user.UserRegistrationRequestDto;
import com.example.bookstore.dto.user.UserResponseDto;
import com.example.bookstore.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(config = MapperConfig.class)
@Component
public interface UserMapper {
    UserResponseDto toUserResponse(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
