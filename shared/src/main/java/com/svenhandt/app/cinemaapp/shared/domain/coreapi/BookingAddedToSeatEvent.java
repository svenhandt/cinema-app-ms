package com.svenhandt.app.cinemaapp.shared.domain.coreapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingAddedToSeatEvent {

    private String seatId;
    private String bookingId;

}
