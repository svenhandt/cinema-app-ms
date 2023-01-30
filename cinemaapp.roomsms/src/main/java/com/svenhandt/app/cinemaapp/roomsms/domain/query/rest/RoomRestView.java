package com.svenhandt.app.cinemaapp.roomsms.domain.query.rest;

import lombok.Data;

import java.util.List;

@Data
public class RoomRestView {

    private String id;
    private String roomName;
    private List<SeatRowRestView> seatRows;

}
