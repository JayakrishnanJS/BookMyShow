package com.jkdev.bookmyshow.service;

import com.jkdev.bookmyshow.model.Show;
import com.jkdev.bookmyshow.model.ShowSeat;
import com.jkdev.bookmyshow.model.ShowSeatType;
import com.jkdev.bookmyshow.repository.ShowSeatTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {

    private final ShowSeatTypeRepository showSeatTypeRepository;

    PricingService(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculatePrice(Show show, List<ShowSeat> showSeats) {
        int amount = 0;
        // get all show seat types for the show
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        // calculate the total amount based on seat types and their prices
        for(ShowSeat showSeat : showSeats) {
            for(ShowSeatType showSeatType : showSeatTypes) {
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
                    amount += showSeatType.getPrice();
                    break; // exit the inner loop once a match is found
                }
            }
        }
        return amount;
    }
}
