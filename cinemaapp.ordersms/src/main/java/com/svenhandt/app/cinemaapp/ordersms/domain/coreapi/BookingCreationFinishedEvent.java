package com.svenhandt.app.cinemaapp.ordersms.domain.coreapi;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookingCreationFinishedEvent {

    private String bookingId;
    private List<String> seatIds;

}
