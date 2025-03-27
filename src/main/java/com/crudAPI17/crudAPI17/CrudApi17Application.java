package com.crudAPI17.crudAPI17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@SpringBootApplication
@EnableTransactionManagement
public class CrudApi17Application {

	public static void main(String[] args) {
		SpringApplication.run(CrudApi17Application.class, args);
	}

	@Bean
	public AbstractPlatformTransactionManager PlatformTransactionManager (MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
}
