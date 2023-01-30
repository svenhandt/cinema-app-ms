package com.svenhandt.app.cinemaapp.roomsms.domain.query.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
public class RoomView {

    @Id
    private String id;

    private String roomName;

    @OneToMany(mappedBy = "room",
            cascade = CascadeType.ALL)
    private List<SeatRowView> seatRows;

}
