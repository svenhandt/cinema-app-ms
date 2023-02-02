package com.svenhandt.app.cinemaapp.ordersms.domain.query.rest;

import lombok.Data;

@Data
public class SeatRestView {

    private String id;
    private int seatRow;
    private int numberInSeatRow;

}
