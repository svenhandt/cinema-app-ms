package com.svenhandt.app.cinemaapp.ordersms.domain.saga;

import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.SeatCreatedEvent;
import com.svenhandt.app.cinemaapp.shared.domain.coreapi.AddBookingToSeatCommand;
import com.svenhandt.app.cinemaapp.shared.domain.coreapi.BookingAddedToSeatEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BookingSaga {

    private static final Logger LOG = LoggerFactory.getLogger(BookingSaga.class);

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "bookingId")
    public void handle(SeatCreatedEvent event) {
        AddBookingToSeatCommand command = AddBookingToSeatCommand
                .builder()
                .roomId(getRoomIdFromSeatId(event.getId()))
                .seatId(event.getId())
                .bookingId(event.getBookingId())
                .build();
        commandGateway.send(command, (commandMessage, commandResultMessage) -> {
            if(commandResultMessage.isExceptional()) {
                //...
            }
        });
    }

    private String getRoomIdFromSeatId(String seatId) {

        //seat id is built up in the format <room_id>_<seat_row>_<number_in_seat_row>
        String[] seatIdArr = seatId.split("_");
        StringBuilder resultBuilder = new StringBuilder();
        int endIndex = seatIdArr.length - 2;
        for(int i=0; i < endIndex; i++) {
            resultBuilder.append(seatIdArr[i]);
            if(i < endIndex - 1) {
                resultBuilder.append("_");
            }
        }
        return resultBuilder.toString();
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "bookingId")
    public void handle(BookingAddedToSeatEvent event) {
        LOG.info("Booking with id " + event.getBookingId() + " added to seat " + event.getSeatId());
    }

}
