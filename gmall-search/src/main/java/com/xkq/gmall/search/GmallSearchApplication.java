package com.xkq.gmall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class GmallSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallSearchApplication.class, args);
	}

}
