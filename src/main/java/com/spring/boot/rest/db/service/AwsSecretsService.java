package com.spring.boot.rest.db.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.rest.db.config.AwsSecretConfig;

import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
public class AwsSecretsService {

    @Value("${aws.secret.name}")
    private String secretName;
    
    @Autowired
    AwsSecretConfig awsSecretConfig;
	
    public Map<String, Object> getSecret() {

        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = awsSecretConfig.getSecretsManagerClient().getSecretValue(request);

        String secretString = response.secretString();

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(secretString, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing secret", e);
        }
    }
}