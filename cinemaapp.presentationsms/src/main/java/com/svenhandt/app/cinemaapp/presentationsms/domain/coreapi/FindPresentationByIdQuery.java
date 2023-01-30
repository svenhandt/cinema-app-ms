package com.svenhandt.app.cinemaapp.presentationsms.domain.coreapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPresentationByIdQuery {

    private String presentationId;

}
