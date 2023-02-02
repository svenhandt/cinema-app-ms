package com.svenhandt.app.cinemaapp.shared.domain.coreapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingRemovedFromSeatEvent {

    private String seatId;
    private String bookingId;

}
