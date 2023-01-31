package com.svenhandt.app.cinemaapp.ordersms.domain.coreapi;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateBookingCommand {

    @TargetAggregateIdentifier
    private String id;

    private String presentationId;
    private List<String> seatIds;
    private String filmName;
    private String roomName;
    private String weekDay;
    private String startTime;
    private BigDecimal totalPrice;
    private String cardNo;

}
