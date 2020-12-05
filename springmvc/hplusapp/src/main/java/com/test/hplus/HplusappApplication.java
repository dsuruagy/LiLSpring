package com.test.hplus;

import com.test.hplus.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HplusappApplication extends ServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HplusappApplication.class, args);
	}

}
