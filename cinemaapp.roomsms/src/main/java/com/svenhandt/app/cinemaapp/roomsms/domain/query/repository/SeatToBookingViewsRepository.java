package com.svenhandt.app.cinemaapp.roomsms.domain.query.repository;

import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.SeatToBookingView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatToBookingViewsRepository extends JpaRepository<SeatToBookingView, String> {

}
