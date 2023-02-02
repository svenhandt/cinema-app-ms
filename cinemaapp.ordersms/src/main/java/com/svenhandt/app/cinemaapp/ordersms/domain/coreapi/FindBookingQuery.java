package com.svenhandt.app.cinemaapp.ordersms.domain.coreapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindBookingQuery {

    private String bookingId;

}
