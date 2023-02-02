package com.svenhandt.app.cinemaapp.ordersms.domain.query;

import com.svenhandt.app.cinemaapp.ordersms.domain.coreapi.FindBookingQuery;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.BookingView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.repository.BookingViewsRepository;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.BookingRestView;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("booking")
public class BookingViewsQueryHandler {

    @Autowired
    private BookingViewsRepository bookingViewsRepository;

    @Autowired
    private BookingRestViewCreatorBean bookingRestViewCreatorBean;

    @QueryHandler
    public BookingRestView findBookingViewById(FindBookingQuery query) {
        String bookingId = query.getBookingId();
        BookingView bookingView = bookingViewsRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalStateException("No booking with id " + bookingId + " found!"));
        BookingRestView bookingRestView = bookingRestViewCreatorBean.createFrom(bookingView);
        return bookingRestView;
    }

}
