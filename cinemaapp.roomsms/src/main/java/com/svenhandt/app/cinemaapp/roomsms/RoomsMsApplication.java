package com.svenhandt.app.cinemaapp.roomsms;

import com.svenhandt.app.cinemaapp.roomsms.domain.command.interceptor.CreateRoomCommandInterceptor;
import com.thoughtworks.xstream.XStream;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RoomsMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomsMsApplication.class, args);
	}

	@Bean
	public XStream xStream() {
		XStream xStream = new XStream();
		xStream.allowTypesByWildcard(new String[]{
				"com.svenhandt.app.cinemaapp.roomsms.**"
		});
		return xStream;
	}

}
