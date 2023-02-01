package com.svenhandt.app.cinemaapp.roomsms.domain.query;

import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.FindRoomsQuery;
import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.RoomCreatedEvent;
import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.SeatForRoomCreatedEvent;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.RoomView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.SeatRowView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.entity.SeatView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.enums.SeatType;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.repository.RoomViewsRepository;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.repository.SeatRowViewsRepository;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.repository.SeatViewsRepository;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.rest.RoomRestView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.rest.SeatRestView;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.rest.SeatRowRestView;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ProcessingGroup("room")
public class RoomViewsProjection {

    @Autowired
    private RoomViewsRepository roomViewsRepository;

    @Autowired
    private SeatRowViewsRepository seatRowViewsRepository;

    @Autowired
    private SeatViewsRepository seatViewsRepository;


    @EventHandler
    public void on(RoomCreatedEvent event) {
        RoomView roomView = new RoomView();
        roomView.setId(event.getId());
        roomView.setRoomName(event.getName());
        roomViewsRepository.save(roomView);
    }

    @EventHandler
    public void on(SeatForRoomCreatedEvent event) {
        RoomView roomView = roomViewsRepository.findById(event.getRoomId())
                .orElseThrow(() -> new IllegalStateException("No roomview found for id " + event.getRoomId()));
        String seatRowViewId = roomView.getId() + "_" + event.getSeatRow();
        SeatRowView seatRowView = getOrCreateSeatRowView(seatRowViewId, roomView, event);
        addNewSeatToRow(seatRowView, event);
        roomViewsRepository.save(roomView);
    }

    private SeatRowView getOrCreateSeatRowView(String seatRowViewId, RoomView roomView, SeatForRoomCreatedEvent event) {
        SeatRowView result;
        Optional<SeatRowView> seatRowViewOpt = seatRowViewsRepository.findById(seatRowViewId);
        if(seatRowViewOpt.isPresent()) {
            result = seatRowViewOpt.get();
        }
        else {
            result = createSeatRowView(seatRowViewId, roomView, event);
        }
        return result;
    }

    private SeatRowView createSeatRowView(String seatRowViewId, RoomView roomView, SeatForRoomCreatedEvent event) {
        SeatRowView seatRowView = new SeatRowView();
        seatRowView.setRoom(roomView);
        seatRowView.setId(seatRowViewId);
        seatRowView.setSeatRow(event.getSeatRow());
        fillSeatRowWithNoneSeats(seatRowView, 1, event.getNumberInSeatRow());
        seatRowViewsRepository.save(seatRowView);
        return seatRowView;
    }

    private void fillSeatRowWithNoneSeats(SeatRowView seatRowView, int startIndex, int endIndex)
    {
        List<SeatView> seatViews = new ArrayList<>();
        for(int i = startIndex; i < endIndex; i++)
        {
            String seatViewId = seatRowView.getId() + "_" + i;
            SeatView seatView = new SeatView();
            seatView.setId(seatViewId);
            seatView.setNumberInSeatRow(i);
            seatView.setSeatType(SeatType.NONE);
            seatView.setSeatRow(seatRowView);
            seatViewsRepository.save(seatView);
        }
        seatRowView.setSeats(seatViews);
    }

    private void addNewSeatToRow(SeatRowView seatRowView, SeatForRoomCreatedEvent event) {
        SeatView seatView = new SeatView();
        seatView.setId(event.getSeatId());
        seatView.setNumberInSeatRow(event.getNumberInSeatRow());
        seatView.setSeatType(SeatType.AVAILABLE);
        seatView.setSeatRow(seatRowView);
        seatViewsRepository.save(seatView);
    }

}
