package com.svenhandt.app.cinemaapp.ordersms.controller;

import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.CreateBookingCommand;
import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.FindBookingQuery;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.SeatView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.BookingRestView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.BookingResultRestView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.SeatRestView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.enums.CreateBookingStatus;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    @PostMapping("/save")
    public ResponseEntity<BookingResultRestView> saveBooking(@RequestBody CreateBookingCommand createBookingCommand) {
        ResponseEntity<BookingResultRestView> result;
        try {
            createBookingId(createBookingCommand);
            commandGateway.sendAndWait(createBookingCommand);
            BookingResultRestView successResultView = createSuccessBookingResult(createBookingCommand.getId());
            result = new ResponseEntity<>(successResultView, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            result = new ResponseEntity<>(createErrorBookingResult(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
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
        if (StringUtils.isEmpty(createBookingCommand.getPresentationId())) {
            throw new IllegalArgumentException("presentationId must not be empty!");
        }
        createBookingCommand.setId(createBookingCommand.getPresentationId() + "_" + UUID.randomUUID());
    }

    @GetMapping("/get")
    public BookingRestView getBooking(@RequestParam("bookingId") String bookingId) {
        SubscriptionQueryResult<BookingRestView, BookingRestView> queryResult = createSubscriptionQuery(bookingId);
        try {
            return getFromQueryResult(queryResult);
        } finally {
            queryResult.close();
        }
    }

    private SubscriptionQueryResult<BookingRestView, BookingRestView> createSubscriptionQuery(String bookingId) {
        SubscriptionQueryResult<BookingRestView, BookingRestView> queryResult = queryGateway
                .subscriptionQuery(new FindBookingQuery(bookingId),
                        ResponseTypes.instanceOf(BookingRestView.class),
                        ResponseTypes.instanceOf(BookingRestView.class)
                );
        return queryResult;
    }

    private BookingRestView getFromQueryResult(SubscriptionQueryResult<BookingRestView, BookingRestView> queryResult) {
        BookingRestView initialResult = queryResult.initialResult().block();
        BookingRestView updatesResult = null;
        if(!seatCountMatches(initialResult)) {
            try {
                updatesResult = queryResult.updates().blockFirst();
            }
            catch(IllegalStateException ex) {
                LOG.warn(ex.getMessage(), ex);
            }
        }
        return updatesResult == null ? initialResult : updatesResult;
    }

    private boolean seatCountMatches(BookingRestView bookingRestView) {
        List<SeatRestView> seatRestViews = bookingRestView.getSeats();
        return seatRestViews != null && bookingRestView.getSeatCount() == seatRestViews.size();
    }
}
