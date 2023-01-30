package com.svenhandt.app.cinemaapp.roomsms.controller;

import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.FindRoomsQuery;
import com.svenhandt.app.cinemaapp.roomsms.domain.query.rest.RoomRestView;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@CrossOrigin("${crossorigin.angular.http}")
public class RoomsQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public RoomRestView getRoomByName(@RequestParam("roomId") String roomId) {
        FindRoomsQuery query = new FindRoomsQuery(roomId);
        RoomRestView roomRestView = queryGateway
                .query(query, ResponseTypes.instanceOf(RoomRestView.class))
                .join();
        return roomRestView;
    }

}
