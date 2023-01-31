package com.svenhandt.app.cinemaapp.ordersms.domain.command;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Aggregate
public class Booking {

    @AggregateIdentifier
    private String id;

    private String cardNo;
    private String filmName;
    private String roomName;
    private String weekDay;
    private String startTime;

    @AggregateMember
    private List<Seat> seats;

}
