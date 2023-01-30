package com.svenhandt.app.cinemaapp.roomsms.domain.query.repository;

import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.RoomView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomViewsRepository extends JpaRepository<RoomView, String> {

}
