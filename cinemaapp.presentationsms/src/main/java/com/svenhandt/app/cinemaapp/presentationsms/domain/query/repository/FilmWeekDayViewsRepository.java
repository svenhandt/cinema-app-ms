package com.svenhandt.app.cinemaapp.presentationsms.domain.query.repository;

import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.FilmWeekDayView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmWeekDayViewsRepository extends JpaRepository<FilmWeekDayView, String> {

}
