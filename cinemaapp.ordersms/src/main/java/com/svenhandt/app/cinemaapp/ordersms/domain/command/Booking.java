package com.svenhandt.app.cinemaapp.ordersms.domain.command;

import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.BookingCreatedEvent;
import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.CreateBookingCommand;
import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.SeatCreatedEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

@Aggregate
public class Booking {

    private static final int SEAT_ID_FORMAT_ARR_MIN_LENGTH = 3;

    @AggregateIdentifier
    private String id;

    private String cardNo;
    private String filmName;
    private String roomName;
    private String weekDay;
    private String startTime;
    private boolean isValid;

    @AggregateMember
    private List<Seat> seats;

    public Booking() {

    }

    @CommandHandler
    public Booking(CreateBookingCommand command) {
        check(command);
        BookingCreatedEvent bookingCreatedEvent = buildBookingCreatedEvent(command);
        List<SeatCreatedEvent> seatCreatedEvents = buildSeatCreatedEvents(command);
        AggregateLifecycle.apply(bookingCreatedEvent);
        seatCreatedEvents.forEach(seatCreatedEvent -> AggregateLifecycle.apply(seatCreatedEvent));
    }

    private void check(CreateBookingCommand command) {
        if(StringUtils.isEmpty(command.getId())) {
            throw new IllegalArgumentException("id must not be empty");
        }
        if(StringUtils.isEmpty(command.getCardNo())) {
            throw new IllegalArgumentException("cardNo must not be empty");
        }
        if(StringUtils.isEmpty(command.getFilmName())) {
            throw new IllegalArgumentException("filmName must not be empty");
        }
        if(StringUtils.isEmpty(command.getStartTime())) {
            throw new IllegalArgumentException("startTime must not be empty");
        }
        if(StringUtils.isEmpty(command.getWeekDay())) {
            throw new IllegalArgumentException("weekDay must not be empty");
        }
        if(command.getTotalPrice() == null) {
            throw new IllegalArgumentException("total price must not be null");
        }
        if(CollectionUtils.isEmpty(command.getSeatIds())) {
            throw new IllegalArgumentException("seat ids must not be empty");
        }
    }

    private BookingCreatedEvent buildBookingCreatedEvent(CreateBookingCommand command) {
        String maskedCardNo = StringUtils.substring(command.getCardNo(), 0, 4) + "************";
        return BookingCreatedEvent
                .builder()
                .id(command.getId())
                .cardNo(maskedCardNo)
                .filmName(command.getFilmName())
                .roomName(command.getRoomName())
                .weekDay(command.getWeekDay())
                .startTime(command.getStartTime())
                .build();
    }

    private List<SeatCreatedEvent> buildSeatCreatedEvents(CreateBookingCommand command) {
        List<SeatCreatedEvent> seatCreatedEvents = new ArrayList<>();
        for(String seatId : command.getSeatIds()) {
            SeatCreatedEvent seatCreatedEvent = buildSeatCreatedEvent(command.getId(), seatId);
            seatCreatedEvents.add(seatCreatedEvent);
        }
        return seatCreatedEvents;
    }

    private SeatCreatedEvent buildSeatCreatedEvent(String bookingId, String seatId) {
        checkSeatIdEmpty(seatId);
        String[] seatIdArr = seatId.split("_");
        checkSeatIdArrCorrectFormat(seatId, seatIdArr);
        String seatRowStr = seatIdArr[seatIdArr.length - 2];
        String numberInSeatRowStr = seatIdArr[seatIdArr.length - 1];
        checkMatchesNumber(seatId, seatRowStr);
        checkMatchesNumber(seatId, numberInSeatRowStr);
        return SeatCreatedEvent
                .builder()
                .id(seatId)
                .bookingId(bookingId)
                .seatRow(Integer.parseInt(seatRowStr))
                .numberInSeatRow(Integer.parseInt(numberInSeatRowStr))
                .build();
    }

    private void checkSeatIdEmpty(String seatId) {
        if(StringUtils.isEmpty(seatId)) {
            throw new IllegalArgumentException("Command contains an empty seatId!");
        }
    }

    private void checkSeatIdArrCorrectFormat(String seatId, String[] seatIdArr) {
        if(seatIdArr.length < SEAT_ID_FORMAT_ARR_MIN_LENGTH) {
            throw new IllegalArgumentException("SeatId " + seatId + " has not the correct format");
        }
    }

    private void checkMatchesNumber(String seatId, String toCheck) {
        if(!toCheck.matches("\\d+")) {
            throw new IllegalArgumentException("SeatId " + seatId + " has not the correct format");
        }
    }

    @EventSourcingHandler
    public void on(BookingCreatedEvent event) {
        this.id = event.getId();
        this.cardNo = event.getCardNo();
        this.filmName = event.getFilmName();
        this.roomName = event.getRoomName();
        this.weekDay = event.getWeekDay();
        this.startTime = event.getStartTime();
        this.isValid = true;
    }

    @EventSourcingHandler
    public void on(SeatCreatedEvent event) {
        if(seats == null) {
            seats = new ArrayList<>();
        }
        Seat seat = new Seat(event.getId(), event.getSeatRow(), event.getNumberInSeatRow());
        seats.add(seat);
    }

}
