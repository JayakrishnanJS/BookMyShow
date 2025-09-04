package com.jkdev.bookmyshow.controller;

import com.jkdev.bookmyshow.dto.BookingTicketRequest;
import com.jkdev.bookmyshow.dto.BookingTicketResponse;
import com.jkdev.bookmyshow.dto.ResponseStatus;
import com.jkdev.bookmyshow.model.Booking;
import com.jkdev.bookmyshow.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // Marks this class as a Spring MVC Controller
public class BookingController {
    private BookingService bookingService;

    @Autowired // Constructor Injection - Spring Boot will automatically inject the BookingService bean
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookingTicketResponse bookTicket(BookingTicketRequest request) {
        BookingTicketResponse response = new BookingTicketResponse();
        try{
            Booking booking = bookingService.bookTicket(request.getShowId(), request.getUserId(), request.getShowSeatIds());
            response.setBookingId(booking.getId());
            response.setMessage("Ticket booking confirmed. Please proceed to payment.");
            response.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
