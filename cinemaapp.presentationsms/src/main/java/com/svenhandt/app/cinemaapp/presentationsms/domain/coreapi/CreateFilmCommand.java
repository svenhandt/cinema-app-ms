package com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateFilmCommand {

    @TargetAggregateIdentifier
    private String filmId;

    private String filmName;
    private String presentationsDataLine;

}
