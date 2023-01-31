package com.svenhandt.app.cinemaapp.ordersms.domain.query.repository;

import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.BookingView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingViewsRepository extends JpaRepository<BookingView, String> {

}
