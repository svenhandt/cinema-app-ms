package com.svenhandt.app.cinemaapp.ordersms.domain.query.rest;

import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.enums.CreateBookingStatus;
import lombok.Data;

@Data
public class BookingResultRestView {

    private String bookingId;
    private CreateBookingStatus createBookingStatus;

}
