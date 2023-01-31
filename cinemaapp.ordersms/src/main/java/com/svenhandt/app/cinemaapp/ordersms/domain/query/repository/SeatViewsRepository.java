package com.svenhandt.app.cinemaapp.ordersms.domain.query.repository;

import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.SeatView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatViewsRepository extends JpaRepository<SeatView, String> {

}
