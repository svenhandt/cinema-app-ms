package com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class PresentationCreatedEvent {

    private String id;
    private String filmId;
    private String filmName;
    private Date startTime;
    private String roomName;
    private BigDecimal price;

}
