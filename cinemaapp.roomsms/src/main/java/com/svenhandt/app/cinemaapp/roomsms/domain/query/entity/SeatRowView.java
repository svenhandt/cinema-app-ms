package com.svenhandt.app.cinemaapp.roomsms.domain.query.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "seatrows")
@Data
public class SeatRowView {

    @Id
    private String id;

    private int seatRow;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "room_id")
    private RoomView room;

    @OneToMany(mappedBy = "seatRow",
            cascade = CascadeType.ALL)
    private List<SeatView> seats;


}
