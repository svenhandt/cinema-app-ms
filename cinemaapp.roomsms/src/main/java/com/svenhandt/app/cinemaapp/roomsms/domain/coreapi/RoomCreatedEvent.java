package com.svenhandt.app.cinemaapp.roomsms.domain.coreapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomCreatedEvent {

    private String id;
    private String name;

}
