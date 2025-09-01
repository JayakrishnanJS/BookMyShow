package com.jkdev.bookmyshow.repository;

import com.jkdev.bookmyshow.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface showRepository extends JpaRepository<Show, Long> {
}
