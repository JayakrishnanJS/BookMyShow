package com.jkdev.bookmyshow.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value // lombok -> create immutable class for value object Address
@Embeddable // JPA -> embeds the value object fields into the parent entity.
@NoArgsConstructor(force = true) // JPA requires this
@AllArgsConstructor // Allows convenient object creation
public class Address {
    String street;
    String city;
    String state;
    String country;
    String postalCode;
    // @Value removes the default no-args constructor (important for JPA)
    // JPA requires a no-args constructor, so add it manually if you're using @Value
}
