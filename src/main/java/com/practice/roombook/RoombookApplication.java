package com.practice.roombook;

import com.practice.roombook.entity.Booking;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RoombookApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RoombookApplication.class, args);
	}

}
