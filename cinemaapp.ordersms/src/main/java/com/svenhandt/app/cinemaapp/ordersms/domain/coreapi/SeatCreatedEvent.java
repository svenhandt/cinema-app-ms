package com.svenhandt.app.cinemaapp.ordersms.domain.coreapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatCreatedEvent {

    private String id;
    private String bookingId;
    private int seatRow;
    private int numberInSeatRow;

}
