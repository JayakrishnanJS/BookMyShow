package com.jkdev.bookmyshow.model;

import com.jkdev.bookmyshow.enums.Language;
import com.jkdev.bookmyshow.enums.MovieFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity//(name = "shows") // "show" is a reserved keyword in SQL
@Table(name = "shows") // physical table name; avoids SQL reserved word conflict
@Getter
@Setter
public class Show extends BaseModel {
    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Screen screen;

    @ManyToOne
    private Theatre theatre;

    private Date time;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowSeat> showSeats;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowSeatType> showSeatTypes;

    @Enumerated(EnumType.STRING) // not a collection, so no need of @ElementCollection
    private Language language;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<MovieFormat> movieFormats;
}

/*
When you have a bidirectional relationship:
- The `@ManyToOne` side is usually the owner of the relationship (has the foreign key)
- The `@OneToMany` side should use `mappedBy` to indicate it's the inverse side

*/