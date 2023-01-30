package com.svenhandt.app.cinemaapp.presentationsms.domain.command;

import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.CreateFilmCommand;
import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.FilmCreatedEvent;
import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.PresentationCreatedEvent;
import com.svenhandt.app.cinemaapp.presentationsms.util.DataTypeConversionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Aggregate
public class Film {

    private static final String COMMA = ",";

    private static final String SLASH = "/";
    private static final String DATA_LINE_INCORRECT_FORMAT = "data line has not the correct format: ";
    private static final int PRESENTATION_DATA_ARR_LENGTH = 4;

    @AggregateIdentifier
    private String id;

    private String name;
    private List<Presentation> presentations;

    public Film() {

    }

    @CommandHandler
    public Film(CreateFilmCommand command) {
        if(StringUtils.isEmpty(command.getFilmId())) {
            throw new IllegalArgumentException("Film id must not be empty.");
        }
        if(StringUtils.isEmpty(command.getFilmName())) {
            throw new IllegalArgumentException("Film name must not be empty.");
        }
        if(StringUtils.isEmpty(command.getPresentationsDataLine())) {
            throw new IllegalArgumentException("Presentations data line must not be empty.");
        }
        createAndApply(command);
    }

    private void createAndApply(CreateFilmCommand command) {
        FilmCreatedEvent filmCreatedEvent = FilmCreatedEvent
                .builder()
                .id(command.getFilmId())
                .name(command.getFilmName())
                .build();
        List<PresentationCreatedEvent> presentationCreatedEvents = createPresentationEvents(command);
        AggregateLifecycle.apply(filmCreatedEvent);
        presentationCreatedEvents.forEach(presentationCreatedEvent -> AggregateLifecycle.apply(presentationCreatedEvent));
    }

    private List<PresentationCreatedEvent> createPresentationEvents(CreateFilmCommand command) {
        List<PresentationCreatedEvent> events = new ArrayList<>();
        String presentationsDataLine = command.getPresentationsDataLine();
        String[] presentationsDataArr = StringUtils.split(presentationsDataLine, COMMA);
        for(String lineForOnePresentation : presentationsDataArr)
        {
            String[] presentationDataArr = StringUtils.split(lineForOnePresentation, SLASH);
            Validate.isTrue(presentationDataArr.length == PRESENTATION_DATA_ARR_LENGTH, DATA_LINE_INCORRECT_FORMAT);
            String dayOfWeekStr = presentationDataArr[0];
            String startTimeStr = presentationDataArr[1];
            String roomName = presentationDataArr[2];
            String priceStr = presentationDataArr[3];
            PresentationCreatedEvent event = createPresentationEvent(command, dayOfWeekStr, startTimeStr, roomName, priceStr);
            events.add(event);
        }
        return events;
    }

    private PresentationCreatedEvent createPresentationEvent(CreateFilmCommand command,  String dayOfWeekStr, String startTimeStr, String roomName, String priceStr)
    {
        Date startTime = DataTypeConversionUtil.getFromWeekAndDay(dayOfWeekStr, startTimeStr);
        return PresentationCreatedEvent
                .builder()
                .filmId(command.getFilmId())
                .filmName(command.getFilmName())
                .roomName(roomName)
                .startTime(startTime)
                .price(DataTypeConversionUtil.getPrice(priceStr))
                .id(command.getFilmId() + StringUtils.lowerCase(roomName.replaceAll(" ", "_"))
                        + startTime.getTime())
                .build();
    }

    @EventSourcingHandler
    public void on(FilmCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
    }

    @EventSourcingHandler
    public void on(PresentationCreatedEvent event) {
        if(this.presentations == null) {
            this.presentations = new ArrayList<>();
        }
        Presentation presentation = new Presentation(
                event.getId(),
                event.getStartTime(),
                event.getRoomName(),
                event.getPrice()
        );
        this.presentations.add(presentation);
    }

}
