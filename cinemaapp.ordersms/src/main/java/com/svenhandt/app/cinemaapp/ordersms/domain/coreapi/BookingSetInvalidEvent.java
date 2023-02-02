package com.svenhandt.app.cinemaapp.ordersms.domain.coreapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingSetInvalidEvent {

    private String bookingId;

}
