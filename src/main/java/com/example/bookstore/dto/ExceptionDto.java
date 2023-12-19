package com.example.bookstore.dto;

import org.springframework.http.HttpStatus;

public record ExceptionDto(HttpStatus httpStatus, String message) {
}
