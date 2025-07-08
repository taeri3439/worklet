package com.project2.worklet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //스케쥴러
public class WorkletApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkletApplication.class, args);
	}

}
