package com.svenhandt.app.cinemaapp.presentationsms.domain.query.repository;

import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.FilmView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmViewsRepository extends JpaRepository<FilmView, String> {

}
