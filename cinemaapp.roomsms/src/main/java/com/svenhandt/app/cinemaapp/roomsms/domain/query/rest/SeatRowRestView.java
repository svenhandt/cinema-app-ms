package com.svenhandt.app.cinemaapp.roomsms.domain.query.rest;

import lombok.Data;

import java.util.List;

@Data
public class SeatRowRestView {

    private String id;
    private int seatRow;
    private List<SeatRestView> seats;

}
