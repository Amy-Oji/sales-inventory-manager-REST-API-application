package com.amyojiakor.sales.inventory.manager.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka // this is required to enable Kafka listener annotations
@ComponentScan(basePackages = "com.amyojiakor.sales.inventory.manager.app")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
