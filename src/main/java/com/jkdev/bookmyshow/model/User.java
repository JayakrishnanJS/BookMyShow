package com.jkdev.bookmyshow.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseModel {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;

    @OneToMany(mappedBy = "bookedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}
