package com.jkdev.bookmyshow.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Artist extends BaseModel{
    private String name;
    private String about;
}
