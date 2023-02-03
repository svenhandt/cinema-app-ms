package com.svenhandt.app.cinemaapp.roomsms.domain.query.rest;

import com.svenhandt.app.cinemaapp.roomsms.domain.query.enums.SeatType;
import lombok.Data;

import java.util.List;

@Data
public class SeatRestView {

    private String id;
    private int numberInSeatRow;
    private SeatType seatType;
    private List<String> bookingIds;

}
