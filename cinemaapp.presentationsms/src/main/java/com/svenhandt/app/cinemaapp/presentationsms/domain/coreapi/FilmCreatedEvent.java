package com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilmCreatedEvent {

    private String id;
    private String name;

}
