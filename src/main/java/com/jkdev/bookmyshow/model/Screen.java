package com.jkdev.bookmyshow.model;

import com.jkdev.bookmyshow.enums.MovieFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Screen extends BaseModel {
    private String name;

    @Enumerated(EnumType.STRING) // to tell jpa that this is an enum
    @ElementCollection // to tell jpa that this is a collection of elements (cardinality - ManyToMany)
    private List<MovieFormat> movieFormats;

    // unidirectional mapping -> No join table; FK lives in seat.screen_id
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true) // to tell jpa that this is a one-to-many relationship with show entity
    @JoinColumn(name = "screen_id", nullable = false) // This explicitly defines where the foreign key goes
    private List<Seat> seats;
}

/*

@OneToMany: Declares a collection relationship where one Screen has many Seat entities. Hibernate will load the collection lazily by default (good).
mappedBy = "screen": This side is inverse (non‑owning). The owning side is the Seat entity field named screen (with @ManyToOne and the foreign key column). No join table is created; FK lives in the seat table.
cascade = CascadeType.ALL: Propagates parent operations (PERSIST, MERGE, REMOVE, REFRESH, DETACH) to the seats. Saving a new Screen with new Seats saves all; deleting the Screen deletes its seats.
orphanRemoval = true: If a Seat is removed from the seats collection (or its screen reference is nulled), Hibernate will issue a DELETE for that row. Think aggregate lifecycle management.
Combine cascade = ALL + orphanRemoval = true only when children are not shared elsewhere (valid for seats bound to one screen).

@JoinColumn(name = "screen_id"):
    - This tells Hibernate to create a column named screen_id in the seat table
    - The column will be a foreign key referencing the primary key of the screen table
    - No join table will be created

When to use `nullable = false`:
- Use this when every `Seat` MUST belong to a `Screen` (which appears to be your case)

Main cascade types (from jakarta.persistence.CascadeType):

PERSIST: When parent is saved (EntityManager.persist / repository.save), unsaved children are also persisted.
MERGE: When parent is merged/updated, detached children are merged too.
REMOVE: Deleting parent also deletes children (issues DELETEs); different from orphanRemoval.
REFRESH: Refreshing parent from DB refreshes children.
DETACH: Detaching parent detaches children.
ALL: Shorthand for all of the above.

Related concept:
orphanRemoval=true: If a child is removed from the collection (or its parent reference nullified in bidirectional mapping),
it is deleted. Think of it as cascading REMOVE triggered by disassociation, not just parent delete.

The ManyToOne side is the owning side and has the foreign key column using @JoinColumn.
The OneToMany side is inverse and uses mappedBy pointing to the owning field name.
You do not put @JoinColumn on the OneToMany side in a bidirectional mapping.
If you make the OneToMany unidirectional (no back reference), then you may use @JoinColumn there, but that creates extra UPDATEs (or a join table if you omit it).

Summary:
Bidirectional: parent has @OneToMany(mappedBy="parent"), child has @ManyToOne @JoinColumn.
Unidirectional: only one side; for collection side use @OneToMany + @JoinColumn (or accept join table).
*/