package com.svenhandt.app.cinemaapp.roomsms.domain.coreapi;


import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@Builder
public class CreateRoomCommand {

    @TargetAggregateIdentifier
    private final String targetId;

    private String fileName;
    private final String seatSymbol;
    private final String initRoomsFilesDirPath;

}
