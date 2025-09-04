package com.jkdev.bookmyshow.service;

import com.jkdev.bookmyshow.enums.BookingStatus;
import com.jkdev.bookmyshow.enums.ShowSeatStatus;
import com.jkdev.bookmyshow.exception.*;
import com.jkdev.bookmyshow.model.Booking;
import com.jkdev.bookmyshow.model.Show;
import com.jkdev.bookmyshow.model.ShowSeat;
import com.jkdev.bookmyshow.model.User;
import com.jkdev.bookmyshow.repository.BookingRepository;
import com.jkdev.bookmyshow.repository.ShowRepository;
import com.jkdev.bookmyshow.repository.ShowSeatRepository;
import com.jkdev.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service // This annotation is used to indicate that the class provides some business functionalities.
// This is a specialization of @Component, which allows Spring to automatically detect our implementation classes through classpath scanning.
@Transactional(isolation = Isolation.SERIALIZABLE)
public class BookingService {

    private final PricingService pricingService;
    private final UserService userService;
    private ShowRepository showRepository;
    private UserRepository userRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(ShowRepository showRepository, UserRepository userRepository, ShowSeatRepository showSeatRepository, BookingRepository bookingRepository, PricingService pricingService, UserService userService) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.pricingService = pricingService;
        this.userService = userService;
    }

    // bookTicket can be used in other places too, so keeping it in the service layer and not tightly coupled with controller layer or dtos => input params are primitive types instead of dto object
    /*
     Transactional annotation is used to manage transactions in Spring. It can be applied to a method or a class.
     When applied to a method, it indicates that the method should be executed within a transactional context.
     If any exception occurs during the execution of the method, the transaction will be rolled back.
     When applied to a class, it indicates that all methods within the class should be executed within a transactional context.
     Here we are applying it to the method because we want to manage the transaction for this specific method only. But actually
     it should be btw step 4 and step 6, but it is complex to implement that way, so we are keeping it simple for now.
     */
    @Transactional
    public Booking bookTicket(Long showId, Long userId, List<Long> showSeatIds) throws UserNotFoundException, ShowNotFoundException, SeatNotAvailableException {

        // 0. check if user is logged in
        if(!userService.isUserLoggedIn()) {
            throw new UserNotLoggedInException("User not logged in. Please login to book tickets.");
        }

        // 1. get user from db
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UserNotFoundException("User with id: " +userId+ " not found"));

        // 2. get show from db
        Optional<Show> showOptional = showRepository.findById(showId);
        Show show = showOptional.orElseThrow(() -> new ShowNotFoundException("Show with id: " +showId+ " not found"));

        // 3. get ShowSeats from db
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        // --------------- Transaction starts here --------------- take a lock here ----------
        // 4. check if all the seats are available, if not throw exception
        for(ShowSeat showSeat : showSeats) {
            if(!showSeat.getStatus().equals(ShowSeatStatus.AVAILABLE)) {
                throw new SeatNotAvailableException("Seat with id: " +showSeat.getId()+ " is not available");
            }
        }
        // 5. if yes(no exception caught in above for loop), mark those seats as booked
        for(ShowSeat showSeat : showSeats) {
            showSeat.setStatus(ShowSeatStatus.BLOCKED);
        }

        // ---------------- Transaction ends here ---------------- unlock here ------------

        // 6. save the seats in DB
        showSeatRepository.saveAll(showSeats); // save() and saveAll() is an Upsert(Update or Insert) operation
        // saveAll() can be used for Insert/Update/Delete operations. It saves all given entities and returns the saved entities.
        // If the entity has an ID that exists in the database, it performs an update; if the ID is new or null, it performs an insert.


        // 7. create a booking with PENDING status
        Booking booking = new Booking();
        booking.setBookedBy(user);
        booking.setBookingDate(new Date());
        booking.setBookedSeats(showSeats);
        booking.setPayments(new ArrayList<>());
        booking.setTotalAmount(pricingService.calculatePrice(show, showSeats));
        booking.setBookingStatus(BookingStatus.PENDING);

        // 8. save the booking
        bookingRepository.save(booking);

        // 9. return the booking
        return booking;
    }
}
