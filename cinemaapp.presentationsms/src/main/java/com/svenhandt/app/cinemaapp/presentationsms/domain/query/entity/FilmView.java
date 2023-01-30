package com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "films")
@Data
public class FilmView {

    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "film",
            cascade = CascadeType.ALL)
    private List<FilmWeekDayView> filmWeekDays;

}
