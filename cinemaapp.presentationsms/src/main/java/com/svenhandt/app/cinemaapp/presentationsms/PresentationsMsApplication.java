package com.svenhandt.app.cinemaapp.presentationsms;

import com.thoughtworks.xstream.XStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PresentationsMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PresentationsMsApplication.class, args);
	}

	@Bean
	public XStream xStream() {
		XStream xStream = new XStream();
		xStream.allowTypesByWildcard(new String[]{
				"com.svenhandt.app.cinemaapp.presentationsms.**"
		});
		return xStream;
	}

}
