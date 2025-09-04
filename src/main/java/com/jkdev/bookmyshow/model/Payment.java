package com.jkdev.bookmyshow.model;

import com.jkdev.bookmyshow.enums.PaymentMode;
import com.jkdev.bookmyshow.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Payment extends BaseModel{
    private Date date;
    private int amount;
    private String refNumber;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @ManyToOne
    private Booking booking;
}
