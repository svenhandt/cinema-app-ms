package com.svenhandt.app.cinemaapp.roomsms.domain.query.repository;

import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.SeatView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatViewsRepository extends JpaRepository<SeatView, String> {

}
