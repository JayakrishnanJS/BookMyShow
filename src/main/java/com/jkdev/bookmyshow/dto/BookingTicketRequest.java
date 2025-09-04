package com.jkdev.bookmyshow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingTicketRequest {
    private Long showId;
    private Long userId;
    private List<Long> showSeatIds;
}
