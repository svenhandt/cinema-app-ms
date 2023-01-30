package com.svenhandt.app.cinemaapp.presentationsms.domain.command;

import org.axonframework.modelling.command.EntityId;

import java.math.BigDecimal;
import java.util.Date;

public class Presentation {

    @EntityId
    private String id;
    private Date startTime;
    private String roomName;
    private BigDecimal price;

    public Presentation() {
    }

    public Presentation(String id, Date startTime, String roomName, BigDecimal price) {
        this.id = id;
        this.startTime = startTime;
        this.roomName = roomName;
        this.price = price;
    }
}
