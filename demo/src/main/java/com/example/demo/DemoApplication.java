package com.example.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.demo.data.GlobalData;




@SpringBootApplication
@MapperScan(value = { "com.example.demo.mapper" })
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		GlobalData globalData;
		SpringApplication.run(DemoApplication.class, args);
	}

	// @Bean
	// public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
	// 	SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	// 	sessionFactory.setDataSource(dataSource);
	// 	sessionFactory.setMapperLocations(
	// 			new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
	// 	return sessionFactory.getObject();
	// }

}
