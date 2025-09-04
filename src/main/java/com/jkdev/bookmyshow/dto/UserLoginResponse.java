package com.jkdev.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    private ResponseStatus responseStatus;
    private String message;
}
