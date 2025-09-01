package com.jkdev.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Theatre extends BaseModel {
    private String name;

    @Embedded
    private Address address;

    @ManyToOne
    private Region region;

    // unidirectional mapping as we don't need to get theatre from screen
    // cascade all as if we delete theatre, all screens should be deleted
    // orphan removal true as if we remove screen from theatre, it should be deleted from DB
    // join column theatre_id will be added to screen table
    // nullable false as screen must belong to a theatre
    // we can have multiple screens in a theatre, so List
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "theatre_id", nullable = false)
    private List<Screen> screens;
}
