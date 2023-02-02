package com.svenhandt.app.cinemaapp.ordersms.domain.query.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "bookings")
@Data
public class BookingView {

    @Id
    private String id;

    private String name;
    private String cardNo;
    private String filmName;
    private String roomName;
    private String weekDay;
    private String startTime;
    private BigDecimal totalPrice;
    private boolean isValid;

    @OneToMany(mappedBy = "bookingView",
            cascade = CascadeType.ALL)
    private List<SeatView> seatViews;

}
