package com.svenhandt.app.cinemaapp.ordersms.domain.query.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "seats")
@Data
public class SeatView {

    @Id
    private String id;

    private int seatRow;
    private int numberInSeatRow;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "booking_id")
    private BookingView bookingView;

}
