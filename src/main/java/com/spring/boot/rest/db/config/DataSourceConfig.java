package com.spring.boot.rest.db.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.boot.rest.db.service.AwsSecretsService;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

    private final AwsSecretsService awsSecretsService;

    public DataSourceConfig(AwsSecretsService awsSecretsService) {
        this.awsSecretsService = awsSecretsService;
    }

    @Bean
    public DataSource dataSource() {

    	 try {
    	Map<String, Object> secret = awsSecretsService.getSecret();
    	System.out.println(secret);
    	String host = secret.get("host").toString();
    	String port = secret.get("port").toString();
    	String dbName = secret.get("dbInstanceIdentifier").toString();
    	String username = secret.get("username").toString();
    	String password = secret.get("password").toString();
        String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.getConnection().close(); // remove this code if you want to connect to AWS RDS postgres db

        return dataSource;
    	 } catch (Exception e) {
    		 System.out.println("RDS failed, switching to local DB");

    	        HikariDataSource localDs = new HikariDataSource();
    	        localDs.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
    	        localDs.setUsername("postgres");
    	        localDs.setPassword("dbo");

    	        return localDs;
         }
    }
}