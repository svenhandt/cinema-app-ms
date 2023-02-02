package com.svenhandt.app.cinemaapp.shared.domain.coreapi;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class RemoveBookingFromSeatCommand {

    @TargetAggregateIdentifier
    private String roomId;

    private String seatId;
    private String bookingId;

}
