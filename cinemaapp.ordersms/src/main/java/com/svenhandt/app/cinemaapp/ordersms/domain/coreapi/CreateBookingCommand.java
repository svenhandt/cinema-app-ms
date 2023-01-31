package com.svenhandt.app.cinemaapp.ordersms.domain.coreapi;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateBookingCommand {

    @TargetAggregateIdentifier
    private String id;

    private List<String> seatIds;
    private String filmName;
    private String roomName;
    private String weekDay;
    private String startTime;
    private BigDecimal totalPrice;
    private String cardNo;

}
