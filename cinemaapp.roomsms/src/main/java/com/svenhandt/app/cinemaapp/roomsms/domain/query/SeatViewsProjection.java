package com.svenhandt.app.cinemaapp.roomsms.domain.query;

import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.SeatToBookingView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.SeatView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.enums.SeatType;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.repository.SeatToBookingViewsRepository;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.repository.SeatViewsRepository;
import com.svenhandt.app.cinemaapp.shared.domain.coreapi.BookingAddedToSeatEvent;
import com.svenhandt.app.cinemaapp.shared.domain.coreapi.BookingRemovedFromSeatEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("room")
public class SeatViewsProjection {

    @Autowired
    private SeatViewsRepository seatViewsRepository;

    @Autowired
    private SeatToBookingViewsRepository seatToBookingViewsRepository;

    @EventHandler
    public void on(BookingAddedToSeatEvent event) {
        String seatId = event.getSeatId();
        SeatView seatView = seatViewsRepository.findById(seatId)
                .orElseThrow(() -> new IllegalStateException("No seat with id " + seatId + " found!"));
        createSeatToBookingView(event, seatView);
        seatViewsRepository.save(seatView);
    }

    @EventHandler
    public void on(BookingRemovedFromSeatEvent event) {
        String seatToBookingViewId = event.getSeatId() + "__" + event.getBookingId();
        seatToBookingViewsRepository.deleteById(seatToBookingViewId);
    }
    private void createSeatToBookingView(BookingAddedToSeatEvent event, SeatView seatView) {
        String id = seatView.getId() + "__" + event.getBookingId();
        SeatToBookingView seatToBookingView = new SeatToBookingView();
        seatToBookingView.setId(id);
        seatToBookingView.setSeatView(seatView);
        seatToBookingView.setBookingId(event.getBookingId());
        seatToBookingViewsRepository.save(seatToBookingView);
    }

}
