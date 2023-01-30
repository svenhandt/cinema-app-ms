package com.svenhandt.app.cinemaapp.roomsms.domain.command;

import org.axonframework.modelling.command.EntityId;

import java.util.List;

public class Seat {

    @EntityId
    private String id;

    private int row;
    private int positionInRow;
    private List<String> presentationIds;

    public Seat() {

    }

    public Seat(String id, int row, int positionInRow) {
        this.id = id;
        this.row = row;
        this.positionInRow = positionInRow;
    }
}
