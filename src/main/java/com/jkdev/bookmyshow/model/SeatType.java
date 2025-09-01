package com.jkdev.bookmyshow.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
// cannot be enum as we need to store it in DB since theatres can have different seat types
public class SeatType extends BaseModel {
    String name;
}
