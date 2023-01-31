package com.svenhandt.app.cinemaapp.ordersms.controller;

import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.CreateBookingCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("${crossorigin.angular.http}")
@RestController
public class BookingController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/booking/save")
    public ResponseEntity<Void> saveBooking(@RequestBody CreateBookingCommand createBookingCommand) {
        ResponseEntity<Void> result;
        try {
            commandGateway.sendAndWait(createBookingCommand);
            result = new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(RuntimeException ex) {
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
