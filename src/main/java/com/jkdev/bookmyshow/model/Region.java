package com.jkdev.bookmyshow.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Region extends BaseModel {
    private String name;
}
