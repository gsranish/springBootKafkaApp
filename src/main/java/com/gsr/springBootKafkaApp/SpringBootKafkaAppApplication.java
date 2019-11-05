package com.gsr.springBootKafkaApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootKafkaAppApplication {

	public static void main(String[] args) {
		System.out.println("App Name : SpringBootKafkaAppApplication");
		SpringApplication.run(SpringBootKafkaAppApplication.class, args);
	}

}
