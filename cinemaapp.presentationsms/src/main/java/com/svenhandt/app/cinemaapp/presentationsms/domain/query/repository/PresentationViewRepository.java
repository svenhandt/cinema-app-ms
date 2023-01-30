package com.svenhandt.app.cinemaapp.presentationsms.domain.query.repository;

import com.svenhandt.app.cinemaapp.presentationsms.domain.query.entity.PresentationView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentationViewRepository extends JpaRepository<PresentationView, String> {

}
