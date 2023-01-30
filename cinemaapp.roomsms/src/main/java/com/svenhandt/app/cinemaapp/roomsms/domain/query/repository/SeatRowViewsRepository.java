package com.svenhandt.app.cinemaapp.roomsms.domain.query.repository;

import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.SeatRowView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRowViewsRepository extends JpaRepository<SeatRowView, String> {

}
