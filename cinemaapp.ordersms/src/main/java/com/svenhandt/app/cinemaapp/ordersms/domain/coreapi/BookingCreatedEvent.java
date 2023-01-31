package com.svenhandt.app.cinemaapp.ordersms.domain.coreapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingCreatedEvent {

    private String id;
    private String cardNo;
    private String filmName;
    private String roomName;
    private String weekDay;
    private String startTime;

}
