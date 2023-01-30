package com.svenhandt.app.cinemaapp.presentationsms.domain.query;

import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.FindFilmsQuery;
import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.FindPresentationByIdQuery;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.FilmView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.FilmWeekDayView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.PresentationView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.repository.FilmViewsRepository;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.repository.PresentationViewRepository;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.rest.FilmRestView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.rest.FilmWeekDayRestView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.rest.PresentationRestView;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ProcessingGroup("film")
public class FilmViewsQueryHandler {

    @Autowired
    private FilmViewsRepository filmViewsRepository;

    @Autowired
    private PresentationViewRepository presentationViewRepository;


    @QueryHandler
    public List<FilmRestView> findFilms(FindFilmsQuery query) {
        List<FilmView> filmViews = filmViewsRepository.findAll();
        return getFromFilmViews(filmViews);
    }

    private List<FilmRestView> getFromFilmViews(List<FilmView> filmViews) {
        List<FilmRestView> result = new ArrayList<>();
        if(filmViews != null) {
            for(FilmView filmView : filmViews) {
                FilmRestView filmRestView = new FilmRestView();
                BeanUtils.copyProperties(filmView, filmRestView);
                copyFilmWeekDays(filmView, filmRestView);
                result.add(filmRestView);
            }
        }
        return result;
    }

    private void copyFilmWeekDays(FilmView filmView, FilmRestView filmRestView) {
        List<FilmWeekDayRestView> filmWeekDayRestViews = new ArrayList<>();
        List<FilmWeekDayView> filmWeekDayViews = filmView.getFilmWeekDays();
        if(filmWeekDayViews != null) {
            for(FilmWeekDayView filmWeekDayView : filmWeekDayViews) {
                FilmWeekDayRestView filmWeekDayRestView = new FilmWeekDayRestView();
                BeanUtils.copyProperties(filmWeekDayView, filmWeekDayRestView);
                copyPresentationsForOverview(filmWeekDayView, filmWeekDayRestView);
                filmWeekDayRestViews.add(filmWeekDayRestView);
            }
        }
        filmRestView.setFilmWeekDays(filmWeekDayRestViews);
    }

    private void copyPresentationsForOverview(FilmWeekDayView filmWeekDayView, FilmWeekDayRestView filmWeekDayRestView) {
        List<PresentationRestView> presentationRestViews = new ArrayList<>();
        List<PresentationView> presentationViews = filmWeekDayView.getPresentations();
        if(presentationViews != null) {
            for(PresentationView presentationView : presentationViews) {
                PresentationRestView presentationRestView = new PresentationRestView();
                presentationRestView.setId(presentationView.getId());
                presentationRestView.setStartTime(presentationView.getStartTime());
                presentationRestView.setRoomName(presentationView.getRoomName());
                presentationRestViews.add(presentationRestView);
            }
        }
        filmWeekDayRestView.setPresentations(presentationRestViews);
    }

    @QueryHandler
    public PresentationRestView findPresentationDetailsById(FindPresentationByIdQuery query) {
        PresentationView presentationView = presentationViewRepository.findById(query.getPresentationId())
                .orElseThrow(() -> new IllegalStateException("Presentation with id " + query.getPresentationId() + " not found!"));
        PresentationRestView presentationRestView = new PresentationRestView();
        BeanUtils.copyProperties(presentationView, presentationRestView);
        return presentationRestView;
    }

}
