package com.svenhandt.app.cinemaapp.ordersms;

import com.svenhandt.app.cinemaapp.ordersms.domain.command.interceptor.SetBookingInvalidCommandInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommandBusConfiguration {

    @Autowired
    public void registerSetBookingInvalidCommandInterceptor(ApplicationContext applicationContext, CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(applicationContext.getBean(SetBookingInvalidCommandInterceptor.class));
    }

}
