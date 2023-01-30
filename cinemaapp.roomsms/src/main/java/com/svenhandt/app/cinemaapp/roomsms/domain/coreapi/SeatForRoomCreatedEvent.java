package com.svenhandt.app.cinemaapp.roomsms.domain.coreapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatForRoomCreatedEvent {

    private String roomId;
    private String seatId;
    private int seatRow;
    private int numberInSeatRow;

}
