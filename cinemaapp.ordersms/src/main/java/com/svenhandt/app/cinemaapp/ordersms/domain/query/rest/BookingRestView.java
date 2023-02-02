package com.svenhandt.app.cinemaapp.ordersms.domain.query.rest;

import lombok.Data;

@Data
public class BookingRestView {

    private String id;
    private String cardNo;
    private String filmName;
    private String roomName;
    private String weekDay;
    private String startTime;
    private boolean isValid;

}
