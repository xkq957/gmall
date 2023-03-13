package com.xkq.gmall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xkq.gmall.coupon.dao")
@SpringBootApplication
public class GmallCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallCouponApplication.class, args);
	}

}
