package com.svenhandt.app.cinemaapp.presentationsms.domain.query.rest;

import lombok.Data;

import java.util.List;

@Data
public class FilmRestView {

    private String id;
    private String name;
    private List<FilmWeekDayRestView> filmWeekDays;

}
