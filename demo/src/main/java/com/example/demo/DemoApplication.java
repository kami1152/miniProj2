package com.example.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
@MapperScan(value = { "com.example.demo.mapper" })
public class DemoApplication {

	public static void main(String[] args) {
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
