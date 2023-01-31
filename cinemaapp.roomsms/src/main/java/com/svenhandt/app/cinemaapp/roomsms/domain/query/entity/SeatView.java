package com.svenhandt.app.cinemaapp.roomsms.domain.query.entity;

import com.svenhandt.app.cinemaapp.roomsms.domain.query.enums.SeatType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "seats")
@Data
public class SeatView {

    @Id
    private String id;

    private int numberInSeatRow;
    private SeatType seatType;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "seat_row_id")
    private SeatRowView seatRow;
}
