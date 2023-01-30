package com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "presentations")
@Data
public class PresentationView {

    @Id
    private String id;

    private String filmName;
    private String startTime;
    private String roomName;
    private BigDecimal price;
    private String weekDay;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "film_week_day_id")
    private FilmWeekDayView filmWeekDayView;

    @Override
    public String toString() {
        return "PresentationView{" +
                "id='" + id + '\'' +
                ", filmName='" + filmName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", roomName='" + roomName + '\'' +
                ", price=" + price +
                ", weekDay='" + weekDay + '\'' +
                '}';
    }
}
