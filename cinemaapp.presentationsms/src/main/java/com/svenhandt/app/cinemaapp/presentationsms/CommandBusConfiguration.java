package com.svenhandt.app.cinemaapp.presentationsms;

import com.svenhandt.app.cinemaapp.presentationsms.domain.command.interceptor.CreateFilmCommandInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommandBusConfiguration {

    @Autowired
    public void registerCreateRoomCommandInterceptor(ApplicationContext applicationContext, CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(applicationContext.getBean(CreateFilmCommandInterceptor.class));
    }

}
