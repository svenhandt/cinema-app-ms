package com.svenhandt.app.cinemaapp.ordersms.controller;

import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.CreateBookingCommand;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.BookingResultRestView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.enums.CreateBookingStatus;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/save")
    public ResponseEntity<BookingResultRestView> saveBooking(@RequestBody CreateBookingCommand createBookingCommand) {
        ResponseEntity<BookingResultRestView> result;
        try {
            createBookingId(createBookingCommand);
            commandGateway.sendAndWait(createBookingCommand);
            BookingResultRestView successResultView = createSuccessBookingResult(createBookingCommand.getId());
            result = new ResponseEntity<>(successResultView, HttpStatus.CREATED);
        }
        catch(IllegalArgumentException ex) {
            result = new ResponseEntity<>(createErrorBookingResult(), HttpStatus.BAD_REQUEST);
        }
        catch(RuntimeException ex) {
            result = new ResponseEntity<>(createErrorBookingResult(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    private BookingResultRestView createSuccessBookingResult(String bookingId) {
        BookingResultRestView result = new BookingResultRestView();
        result.setBookingId(bookingId);
        result.setCreateBookingStatus(CreateBookingStatus.OK);
        return result;
    }

    private BookingResultRestView createErrorBookingResult() {
        BookingResultRestView result = new BookingResultRestView();
        result.setCreateBookingStatus(CreateBookingStatus.ERROR);
        return result;
    }

    private void createBookingId(CreateBookingCommand createBookingCommand) {
        if(StringUtils.isEmpty(createBookingCommand.getPresentationId())) {
            throw new IllegalArgumentException("presentationId must not be empty!");
        }
        createBookingCommand.setId(createBookingCommand.getPresentationId() + "_" + UUID.randomUUID());
    }

}
