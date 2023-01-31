package com.svenhandt.app.cinemaapp.ordersms.controller;

import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.CreateBookingCommand;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin("${crossorigin.angular.http}")
@RestController
public class BookingController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/booking/save")
    public ResponseEntity<String> saveBooking(@RequestBody CreateBookingCommand createBookingCommand) {
        ResponseEntity<String> result;
        try {
            createBookingId(createBookingCommand);
            commandGateway.sendAndWait(createBookingCommand);
            result = new ResponseEntity<>(createBookingCommand.getId(), HttpStatus.CREATED);
        }
        catch(IllegalArgumentException ex) {
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch(RuntimeException ex) {
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    private void createBookingId(CreateBookingCommand createBookingCommand) {
        if(StringUtils.isEmpty(createBookingCommand.getPresentationId())) {
            throw new IllegalArgumentException("presentationId must not be empty!");
        }
        createBookingCommand.setId(createBookingCommand.getPresentationId() + "_" + UUID.randomUUID());
    }

}
