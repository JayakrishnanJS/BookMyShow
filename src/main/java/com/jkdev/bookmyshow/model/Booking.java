package com.jkdev.bookmyshow.model;

import com.jkdev.bookmyshow.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Booking extends BaseModel {
    private Date bookingDate;

    @ManyToOne
    private User bookedBy;

    private int numberOfSeats;

    @ManyToMany
    private List<ShowSeat> bookedSeats;
    // Cancellation : One ShowSeat can be present in multiple bookings => one seat can be booked multiple times after cancellation

    // payment has manytoone, so it will have foreign key of booking
    // bidirectional mapping
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments; // one booking can have multiple payments (eg: partial payments, retries)

    @Enumerated(EnumType.STRING) // to store enum as string in db; by default, it stores as ordinal - 1,2,3...
    private BookingStatus bookingStatus;
}
