package com.svenhandt.app.cinemaapp.ordersms.domain.query.repository;

import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.BookingView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.SeatView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatViewsRepository extends JpaRepository<SeatView, String> {

    public List<SeatView> findSeatViewsByBookingView(BookingView bookingView);

}
