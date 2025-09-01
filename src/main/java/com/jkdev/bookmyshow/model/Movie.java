package com.jkdev.bookmyshow.model;

import com.jkdev.bookmyshow.enums.Language;
import com.jkdev.bookmyshow.enums.MovieFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Movie extends BaseModel{
    private String title;
    private String description;
    private Integer duration; // in minutes
    private String language;
    private String genre;
    private String year;
    private String director;

    @ManyToMany
    private List<Artist> cast;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<MovieFormat> movieFormats;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Language> languages;
}
