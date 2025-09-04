package com.jkdev.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpResponse {
    private Long userId;
    private String message;
    private ResponseStatus responseStatus;
}
