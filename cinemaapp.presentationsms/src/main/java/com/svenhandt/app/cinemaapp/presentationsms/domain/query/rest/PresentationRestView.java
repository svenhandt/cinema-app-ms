package com.svenhandt.app.cinemaapp.presentationsms.domain.query.rest;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PresentationRestView {

    private String id;
    private String filmName;
    private String weekDay;
    private String startTime;
    private String roomName;
    private BigDecimal price;

}
