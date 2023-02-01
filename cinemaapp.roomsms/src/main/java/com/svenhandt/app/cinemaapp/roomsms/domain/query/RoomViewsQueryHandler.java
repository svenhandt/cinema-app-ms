package com.svenhandt.app.cinemaapp.roomsms.domain.query;

import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.FindRoomsQuery;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.RoomView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.SeatRowView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.SeatView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.repository.RoomViewsRepository;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.rest.RoomRestView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.rest.SeatRestView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.rest.SeatRowRestView;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ProcessingGroup("room")
public class RoomViewsQueryHandler {

    @Autowired
    private RoomViewsRepository roomViewsRepository;

    @QueryHandler
    public RoomRestView findRoomById(FindRoomsQuery query) {
        RoomView roomView = roomViewsRepository.findById(query.getRoomId())
                .orElseThrow(() -> new  IllegalStateException());
        RoomRestView roomRestView = new RoomRestView();
        BeanUtils.copyProperties(roomView, roomRestView);
        copySeatRows(roomView, roomRestView);
        return roomRestView;
    }

    private void copySeatRows(RoomView roomView, RoomRestView roomRestView) {
        List<SeatRowRestView> seatRowRestViews = new ArrayList<>();
        List<SeatRowView> seatRowViews = roomView.getSeatRows();
        if(seatRowViews != null) {
            for(SeatRowView seatRowView : seatRowViews) {
                SeatRowRestView seatRowRestView = new SeatRowRestView();
                BeanUtils.copyProperties(seatRowView, seatRowRestView);
                copySeats(seatRowView, seatRowRestView);
                seatRowRestViews.add(seatRowRestView);
            }
        }
        roomRestView.setSeatRows(seatRowRestViews);
    }

    private void copySeats(SeatRowView seatRowView, SeatRowRestView seatRowRestView) {
        List<SeatRestView> seatRestViews = new ArrayList<>();
        List<SeatView> seatViews = seatRowView.getSeats();
        if(seatViews != null) {
            for(SeatView seatView : seatViews) {
                SeatRestView seatRestView = new SeatRestView();
                BeanUtils.copyProperties(seatView, seatRestView);
                seatRestViews.add(seatRestView);
            }
        }
        seatRowRestView.setSeats(seatRestViews);
    }

}
