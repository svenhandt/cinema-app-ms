package com.svenhandt.app.cinemaapp.ordersms.domain.saga;

import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.BookingCreationFinishedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BookingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "bookingId")
    public void handle(BookingCreationFinishedEvent event) {

    }

}
