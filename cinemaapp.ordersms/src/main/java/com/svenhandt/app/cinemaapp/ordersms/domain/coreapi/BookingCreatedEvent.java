package com.svenhandt.app.cinemaapp.ordersms.domain.coreapi;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BookingCreatedEvent {

    private String id;
    private String name;
    private String cardNo;
    private String filmName;
    private String roomName;
    private String weekDay;
    private String startTime;
    private BigDecimal totalPrice;

}
