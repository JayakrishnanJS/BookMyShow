package com.jkdev.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingTicketResponse {
    private Long bookingId; // amount is inside booking entity => access it from there
    private String message;
    private ResponseStatus responseStatus;
}
