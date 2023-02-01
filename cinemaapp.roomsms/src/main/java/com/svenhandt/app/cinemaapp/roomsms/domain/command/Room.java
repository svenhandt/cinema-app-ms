package com.svenhandt.app.cinemaapp.roomsms.domain.command;

import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.CreateRoomCommand;
import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.RoomCreatedEvent;
import com.svenhandt.app.cinemaapp.roomsms.domain.coreapi.SeatForRoomCreatedEvent;
import com.svenhandt.app.cinemaapp.shared.domain.coreapi.AddBookingToSeatCommand;
import com.svenhandt.app.cinemaapp.shared.domain.coreapi.BookingAddedToSeatEvent;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Aggregate
public class Room {

    private static final Logger LOG = LoggerFactory.getLogger(Room.class);

    @AggregateIdentifier
    private String id;

    private String name;

    @AggregateMember
    private List<Seat> seats;

    public Room() {

    }

    @CommandHandler
    public Room(CreateRoomCommand command) {
        if (StringUtils.isEmpty(command.getTargetId())) {
            throw new IllegalArgumentException("Target id must not be empty.");
        }
        if (StringUtils.isEmpty(command.getFileName())) {
            throw new IllegalArgumentException("File name must not be empty.");
        }
        if (StringUtils.isEmpty(command.getSeatSymbol())) {
            throw new IllegalArgumentException("Seat symbol must not be empty.");
        }
        if (StringUtils.isEmpty(command.getInitRoomsFilesDirPath())) {
            throw new IllegalArgumentException("Init rooms file dir path must not be empty.");
        }
        RoomCreatedEvent roomCreatedEvent = create(command);
        List<SeatForRoomCreatedEvent> seatForRoomCreatedEvents = createSeats(command);
        AggregateLifecycle.apply(roomCreatedEvent);
        seatForRoomCreatedEvents.forEach(event -> AggregateLifecycle.apply(event));
    }

    private RoomCreatedEvent create(CreateRoomCommand command) {
        String roomId = command.getTargetId();
        String roomName = StringUtils.capitalize(roomId.replaceAll("_", " "));
        LOG.info("Create room {}", roomId);
        RoomCreatedEvent roomCreatedEvent = RoomCreatedEvent
                .builder()
                .id(roomId)
                .name(roomName)
                .build();
        return roomCreatedEvent;
    }

    private List<SeatForRoomCreatedEvent> createSeats(CreateRoomCommand command) {
        List<SeatForRoomCreatedEvent> seatForRoomCreatedEvents = new ArrayList<>();
        BufferedReader reader = null;
        try {
            URL fileUrl = getClass().getClassLoader().getResource(command.getInitRoomsFilesDirPath()
                    + "/" + command.getFileName());
            URI fileUri = fileUrl.toURI();
            Path path = Paths.get(fileUri);
            reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
            String currentLine;
            int currentSeatRow = 1;
            while ((currentLine = reader.readLine()) != null) {
                seatForRoomCreatedEvents.addAll(
                        createSeatRow(command.getSeatSymbol(), command.getTargetId(), currentSeatRow, currentLine));
                currentSeatRow++;
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return seatForRoomCreatedEvents;
    }

    private List<SeatForRoomCreatedEvent> createSeatRow(String seatSymbol, String roomId, int seatRow, String seatLineStr) {
        List<SeatForRoomCreatedEvent> seatForRoomCreatedEvents = new ArrayList<>();
        for (int i = 0; i < seatLineStr.length(); i++) {
            if (seatLineStr.charAt(i) == seatSymbol.charAt(0)) {
                String seatId = roomId + "_" + seatRow + "_" + (i+1);
                LOG.info("Create seat {}", seatId);
                SeatForRoomCreatedEvent event = SeatForRoomCreatedEvent
                        .builder()
                        .roomId(roomId)
                        .seatId(seatId)
                        .seatRow(seatRow)
                        .numberInSeatRow(i+1)
                        .build();
                seatForRoomCreatedEvents.add(event);
            }
        }
        return seatForRoomCreatedEvents;
    }

    @EventSourcingHandler
    public void on(RoomCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
    }

    @EventSourcingHandler
    public void on(SeatForRoomCreatedEvent event) {
        if(StringUtils.equals(this.id, event.getRoomId())) {
            if(this.seats == null) {
                this.seats = new ArrayList<>();
            }
            Seat seat = new Seat(event.getSeatId(), event.getSeatRow(), event.getNumberInSeatRow());
            this.seats.add(seat);
        }
    }

}
