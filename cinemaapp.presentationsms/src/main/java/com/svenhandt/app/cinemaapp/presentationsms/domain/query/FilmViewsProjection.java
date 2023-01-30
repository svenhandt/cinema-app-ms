package com.svenhandt.app.cinemaapp.presentationsms.domain.query;

import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.FilmCreatedEvent;
import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.PresentationCreatedEvent;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.FilmView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.FilmWeekDayView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.PresentationView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.repository.FilmViewsRepository;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.repository.FilmWeekDayViewsRepository;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.repository.PresentationViewRepository;
import com.svenhandt.app.cinemaapp.presentationsms.util.DataTypeConversionUtil;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ProcessingGroup("film")
public class FilmViewsProjection {

    @Autowired
    private FilmViewsRepository filmViewsRepository;

    @Autowired
    private PresentationViewRepository presentationViewRepository;

    @Autowired
    private FilmWeekDayViewsRepository filmWeekDayViewsRepository;

    @EventHandler
    public void on(FilmCreatedEvent event) {
        FilmView filmView = new FilmView();
        filmView.setId(event.getId());
        filmView.setName(event.getName());
        filmViewsRepository.save(filmView);
    }

    @EventHandler
    public void on(PresentationCreatedEvent event) {
        FilmWeekDayView filmWeekDayView = getOrCreateFilmWeekDayView(event);
        PresentationView presentationView = new PresentationView();
        presentationView.setFilmWeekDayView(filmWeekDayView);
        presentationView.setId(event.getId());
        presentationView.setFilmName(event.getFilmName());
        presentationView.setWeekDay(filmWeekDayView.getWeekdayName());
        presentationView.setRoomName(event.getRoomName());
        presentationView.setStartTime(DataTypeConversionUtil.getTimeOfDayFormatted(event.getStartTime()));
        presentationView.setPrice(event.getPrice());
        presentationViewRepository.save(presentationView);
    }

    private FilmWeekDayView getOrCreateFilmWeekDayView(PresentationCreatedEvent event) {
        FilmWeekDayView result;
        String filmId = event.getFilmId();
        String dayOfWeek = DataTypeConversionUtil.getDayOfWeekAbbrev(event.getStartTime());
        String filmWeekDayViewEventId = filmId + "_" + dayOfWeek;
        Optional<FilmWeekDayView> filmWeekDayViewOpt = filmWeekDayViewsRepository.findById(filmWeekDayViewEventId);
        if(filmWeekDayViewOpt.isPresent()) {
            result = filmWeekDayViewOpt.get();
        }
        else {
            result = createFilmWeekDayView(filmId, filmWeekDayViewEventId, dayOfWeek);
        }
        return result;
    }

    private FilmWeekDayView createFilmWeekDayView(String filmId, String filmWeekDayViewEventId, String dayOfWeek) {
        FilmView filmView = filmViewsRepository
                .findById(filmId)
                .orElseThrow(() -> new IllegalStateException("No FilmView with id " + filmId + " found."));
        FilmWeekDayView filmWeekDayView = new FilmWeekDayView();
        filmWeekDayView.setFilm(filmView);
        filmWeekDayView.setId(filmWeekDayViewEventId);
        filmWeekDayView.setWeekdayName(dayOfWeek);
        filmWeekDayViewsRepository.save(filmWeekDayView);
        return filmWeekDayView;
    }

}
