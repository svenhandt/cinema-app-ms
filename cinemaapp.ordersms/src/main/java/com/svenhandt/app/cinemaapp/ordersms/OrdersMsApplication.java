package com.svenhandt.app.cinemaapp.ordersms;

import com.thoughtworks.xstream.XStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrdersMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersMsApplication.class, args);
	}

	@Bean
	public XStream xStream() {
		XStream xStream = new XStream();
		xStream.allowTypesByWildcard(new String[]{
				"com.svenhandt.app.cinemaapp.ordersms.**",
				"com.svenhandt.app.cinemaapp.shared.**"
		});
		return xStream;
	}

}
