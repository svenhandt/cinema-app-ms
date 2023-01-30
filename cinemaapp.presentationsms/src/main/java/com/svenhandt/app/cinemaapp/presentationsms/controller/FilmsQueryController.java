package com.svenhandt.app.cinemaapp.presentationsms.controller;

import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.FindFilmsQuery;
import com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi.FindPresentationByIdQuery;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.rest.FilmRestView;
import com.svenhandt.app.cinemaapp.presentationsms.domain.query.rest.PresentationRestView;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
@CrossOrigin("${crossorigin.angular.http}")
public class FilmsQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<FilmRestView> getFilms() {
        FindFilmsQuery query = new FindFilmsQuery();
        List<FilmRestView> filmRestViews = queryGateway
                .query(query, ResponseTypes.multipleInstancesOf(FilmRestView.class))
                .join();
        return filmRestViews;
    }

    @GetMapping("/presentations")
    public PresentationRestView getPresentationRestViewById(@RequestParam("presentationId") String presentationId) {
        FindPresentationByIdQuery query = new FindPresentationByIdQuery(presentationId);
        PresentationRestView presentationRestView = queryGateway
                .query(query, ResponseTypes.instanceOf(PresentationRestView.class))
                .join();
        return presentationRestView;
    }

}
