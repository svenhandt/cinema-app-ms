package com.svenhandt.app.cinemaapp.roomsms.domain.command;

import com.svenhandt.app.cinemaapp.shared.domain.coreapi.AddBookingToSeatCommand;
import com.svenhandt.app.cinemaapp.shared.domain.coreapi.BookingAddedToSeatEvent;
import com.svenhandt.app.cinemaapp.shared.domain.coreapi.BookingRemovedFromSeatEvent;
import com.svenhandt.app.cinemaapp.shared.domain.coreapi.RemoveBookingFromSeatCommand;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Seat {

    @EntityId(routingKey = "seatId")
    private String id;

    private int row;
    private int positionInRow;
    private List<String> bookingIds;

    public Seat() {

    }

    public Seat(String id, int row, int positionInRow) {
        this.id = id;
        this.row = row;
        this.positionInRow = positionInRow;
    }

    @CommandHandler
    public void on(AddBookingToSeatCommand command) {
        if(StringUtils.isEmpty(command.getBookingId())) {
            throw new IllegalStateException("BookingId for seatId " + command.getSeatId() + " must not be empty!");
        }
        BookingAddedToSeatEvent bookingAddedToSeatEvent = BookingAddedToSeatEvent
                .builder()
                .seatId(command.getSeatId())
                .bookingId(command.getBookingId())
                .build();
        AggregateLifecycle.apply(bookingAddedToSeatEvent);
    }

    @CommandHandler
    public void on(RemoveBookingFromSeatCommand command) {
        if(StringUtils.isEmpty(command.getBookingId())) {
            throw new IllegalStateException("BookingId for seatId " + command.getSeatId() + " must not be empty!");
        }
        BookingRemovedFromSeatEvent bookingRemovedFromSeatEvent = BookingRemovedFromSeatEvent
                .builder()
                .seatId(command.getSeatId())
                .bookingId(command.getBookingId())
                .build();
        AggregateLifecycle.apply(bookingRemovedFromSeatEvent);
    }

    @EventSourcingHandler
    public void on(BookingAddedToSeatEvent event) {
        if(StringUtils.equals(this.id, event.getSeatId())) {
            if(bookingIds == null) {
                bookingIds = new ArrayList<>();
            }
            bookingIds.add(event.getBookingId());
        }
    }

    @EventSourcingHandler
    public void on(BookingRemovedFromSeatEvent event) {
        if(bookingIds != null && StringUtils.equals(this.id, event.getSeatId())) {
            Iterator<String> bookingIdIterator = bookingIds.iterator();
            while (bookingIdIterator.hasNext()) {
                if(StringUtils.equals(bookingIdIterator.next(), event.getBookingId())) {
                    bookingIdIterator.remove();
                }
            }
        }
    }

}
