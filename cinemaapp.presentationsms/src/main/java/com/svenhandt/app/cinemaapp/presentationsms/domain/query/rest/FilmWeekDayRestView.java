package com.svenhandt.app.cinemaapp.presentationsms.domain.query.rest;

import lombok.Data;

import java.util.List;

@Data
public class FilmWeekDayRestView {

    private String id;
    private String weekdayName;
    private List<PresentationRestView> presentations;

}
