package com.jkdev.bookmyshow.model;

import com.jkdev.bookmyshow.enums.ShowSeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShowSeat extends BaseModel{
    @ManyToOne // Many showSeats can belong to one show
    @JoinColumn(name = "show_id", nullable = false) // Foreign key column in ShowSeat table
    private Show show;

    @ManyToOne // Many showSeats can belong to one seat
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private ShowSeatStatus status;
    //Without @Enumerated, JPA defaults to storing its ordinal (0,1,2,...) which is fragile (changing enum
    // order corrupts data). You explicitly annotate to store the stable name instead.
}

// Sample ShowSeat Data
// Show - Seat - Status
// 1 G1 - Booked
// 1 G2 - Available
// 1 G3 - Available
// 2 G1 - Available
// 2 G2 - Booked
// 2 G3 - Available

/*
## Why Both Approaches Work
### 1. The `Screen`-`Seat` Relationship (Unidirectional One-to-Many)
This approach uses a **unidirectional one-to-many relationship** with a `@JoinColumn`:
- The `Screen` entity knows about its seats but `Seat` doesn't have a reference back to `Screen`
- `@JoinColumn(name = "screen_id")` explicitly tells JPA to create a foreign key column in the `seat` table
- No need for `mappedBy` since it's a unidirectional relationship

### 2. The `Show`-`ShowSeatType` and `SeatType`-`ShowSeatType` Relationships (Bidirectional)
These use a **bidirectional relationship** approach:
- `ShowSeatType` has `@ManyToOne` relationships to both `Show` and `SeatType`
- On the reverse side, `Show` has a collection of `ShowSeatType` with `mappedBy="show"`
- The foreign keys are defined on the `@ManyToOne` side (the "owning" side)

## Key Differences
1. **Directionality**:
    - `Screen`-`Seat`: Unidirectional (only `Screen` knows about `Seat`)
    - `Show`-`ShowSeatType`: Bidirectional (both entities reference each other)

2. **Navigation**:
    - In the unidirectional case, you can only navigate from `Screen` to `Seat`
    - In the bidirectional case, you can navigate in both directions

3. **Foreign Key Control**:
    - In unidirectional with `@JoinColumn`, you explicitly place the FK
    - In bidirectional, the `@ManyToOne` side owns the relationship and has the FK

## Why Both Work
Both approaches are valid JPA patterns. Your choice depends on your application needs:
1. If you need to navigate in both directions → Use bidirectional
2. If you only need to navigate in one direction → Unidirectional is simpler

In the case of `Screen`-`Seat`, you probably don't need to navigate from `Seat` to `Screen` often, so unidirectional is adequate.
For `Show`-`ShowSeatType`, you likely need to navigate back and forth (e.g., "find all seat types for a show" and "find the show for this seat type"), so bidirectional makes sense.
## The Problem With `ShowSeatType`
The issue with `ShowSeatType` wasn't about the approach - it was simply that both relationships used the same column name (`show_id`), which would create a conflict.

* */