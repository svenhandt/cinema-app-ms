package com.svenhandt.app.cinemaapp.ordersms.domain.coreapi;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class SetBookingInvalidCommand {

    @TargetAggregateIdentifier
    private String bookingId;

}
