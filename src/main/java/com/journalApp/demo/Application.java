package com.journalApp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling


public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
	return new MongoTransactionManager(dbFactory);
}
}

//username mongodb=malikagulati015_db_user
//password mongodb=OnJWcUdG9whpBJVg.        9Ei80aE5TManndIS
//connection string=mongodb+srv://malikagulati015_db_user:<OnJWcUdG9whpBJVg>@cluster0.hn3urhz.mongodb.net/?appName=Cluster0
//spring.data.mongodb.uri=mongodb+srv://malikagulati015_db_user:OnJWcUdG9whpBJVg@cluster0.hn3urhz.mongodb.net/?appName=Cluster0

//@Bean
//public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
//	return new MongoTransactionManager(dbFactory);
//}

//mongodb+srv://malikagulati015_db_user:<db_password>@cluster0.hn3urhz.mongodb.net/?appName=Cluster0


//root@12345
//Malika_user
//d643f6ea-180e-4bfc-bae1-60f1284159a1      password for spring security