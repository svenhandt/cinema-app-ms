package com.svenhandt.app.cinemaapp.ordersms.domain.command;

import org.axonframework.modelling.command.EntityId;

public class Seat {

    @EntityId
    private String id;

    private int seatRow;
    private int numberInSeatRow;

}
