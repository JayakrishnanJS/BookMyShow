package com.jkdev.bookmyshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShowSeatType extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_type_id", nullable = false)
    private SeatType seatType;

    private int price;
}

// ShowSeatType
// show, seatType, price
// 1 bronze - 100
// 1 silver - 200
// 1 gold - 300

// 2 bronze - 120
// 2 silver - 220
// 2 gold - 320