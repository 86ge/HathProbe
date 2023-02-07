package com.github.xiny.hathprobe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class HathProbeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HathProbeApplication.class, args);
	}

}
