package com.svenhandt.app.cinemaapp.roomsms.domain.query.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "seat_to_bookings")
@Data
public class SeatToBookingView {

    @Id
    private String id;

    private String bookingId;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "seat_id")
    private SeatView seatView;

}
