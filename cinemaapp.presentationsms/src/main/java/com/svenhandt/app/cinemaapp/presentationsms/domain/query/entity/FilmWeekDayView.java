package com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "filmweekdays")
@Data
public class FilmWeekDayView {

    @Id
    private String id;

    private String weekdayName;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "film_id")
    private FilmView film;

    @OneToMany(mappedBy = "filmWeekDayView",
            cascade = CascadeType.ALL)
    private List<PresentationView> presentations;

}
