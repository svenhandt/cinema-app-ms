package com.svenhandt.app.cinemaapp.ordersms.domain.query;

import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.BookingView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.entity.SeatView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.BookingRestView;
import com.svenhandt.app.cinemaapp.ordersms.domain.query.rest.SeatRestView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingRestViewCreatorBean {

    public BookingRestView createFrom(BookingView bookingView) {
        BookingRestView bookingRestView = new BookingRestView();
        BeanUtils.copyProperties(bookingView, bookingRestView);
        copySeats(bookingView, bookingRestView);
        return bookingRestView;
    }

    private void copySeats(BookingView bookingView, BookingRestView bookingRestView) {
        List<SeatView> seatViews = bookingView.getSeatViews();
        List<SeatRestView> seatRestViews = new ArrayList<>();
        if(seatViews != null) {
            for(SeatView seatView : seatViews) {
                SeatRestView seatRestView = new SeatRestView();
                BeanUtils.copyProperties(seatView, seatRestView);
                seatRestViews.add(seatRestView);
            }
        }
        bookingRestView.setSeats(seatRestViews);
    }

}
