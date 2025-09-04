package com.jkdev.bookmyshow.repository;

import com.jkdev.bookmyshow.model.Show;
import com.jkdev.bookmyshow.model.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
    List<ShowSeatType> findAllByShow(Show show);
}
