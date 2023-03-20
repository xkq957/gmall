package com.xkq.gmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 1、整合MyBatis-Plus
 * 		1）导入依赖
 * 		<dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-boot-starter</artifactId>
 *             <version>3.3.2</version>
 *      </dependency>
 * 2、配置
 * 		1）配置数据源
 * 			1）导入数据库驱动
 * 			2）配置数据源
 * 				1）创建application.yml
 * 		2）配置Mybatis-Plus
 */
@EnableFeignClients(basePackages = "com.xkq.gmall.product.feign")
@SpringBootApplication
@MapperScan("com.xkq.gmall.product.dao")
public class GmallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallProductApplication.class, args);
	}

}
