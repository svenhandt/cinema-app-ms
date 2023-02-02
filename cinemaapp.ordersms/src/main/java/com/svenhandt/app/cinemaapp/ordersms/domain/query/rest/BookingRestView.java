package com.svenhandt.app.cinemaapp.ordersms.domain.query.rest;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingRestView {

    private String id;
    private String name;
    private String cardNo;
    private String filmName;
    private String roomName;
    private String weekDay;
    private String startTime;
    private BigDecimal totalPrice;
    private boolean isValid;

    private List<SeatRestView> seats;

}
