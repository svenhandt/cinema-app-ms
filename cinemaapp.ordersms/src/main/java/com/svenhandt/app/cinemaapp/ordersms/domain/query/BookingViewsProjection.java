package com.svenhandt.app.cinemaapp.ordersms.domain.query;

import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.BookingCreatedEvent;
import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.SeatCreatedEvent;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.BookingView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.SeatView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.repository.BookingViewsRepository;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.repository.SeatViewsRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("booking")
public class BookingViewsProjection {

    @Autowired
    private BookingViewsRepository bookingViewsRepository;

    @Autowired
    private SeatViewsRepository seatViewsRepository;

    @EventHandler
    public void on(BookingCreatedEvent event) {
        BookingView bookingView = new BookingView();
        bookingView.setId(event.getId());
        bookingView.setCardNo(event.getCardNo());
        bookingView.setFilmName(event.getFilmName());
        bookingView.setRoomName(event.getRoomName());
        bookingView.setWeekDay(event.getWeekDay());
        bookingView.setStartTime(event.getStartTime());
        bookingView.setValid(true);
        bookingViewsRepository.save(bookingView);
    }

    @EventHandler
    public void on(SeatCreatedEvent event) {
        String bookingId = event.getBookingId();;
        BookingView bookingView = bookingViewsRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalStateException("No booking with id " + bookingId + " was found!"));
        SeatView seatView = new SeatView();
        seatView.setId(event.getId());
        seatView.setSeatRow(event.getSeatRow());
        seatView.setNumberInSeatRow(event.getNumberInSeatRow());
        seatView.setBookingView(bookingView);
        seatViewsRepository.save(seatView);
    }

}
