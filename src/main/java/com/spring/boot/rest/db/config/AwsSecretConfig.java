package com.spring.boot.rest.db.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Configuration
public class AwsSecretConfig {

	@Value("${aws.region}") 
	private String region;
	
	@Bean
    public SecretsManagerClient getSecretsManagerClient() {
       return SecretsManagerClient.builder()
                .region(Region.of(region))
                .build();
    }
}
