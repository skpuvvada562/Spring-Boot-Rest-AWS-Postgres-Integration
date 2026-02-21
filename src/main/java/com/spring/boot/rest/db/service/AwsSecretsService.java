package com.spring.boot.rest.db.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
public class AwsSecretsService {

    @Value("${aws.secret.name}")
    private String secretName;

    private final SecretsManagerClient secretsManagerClient;

    public AwsSecretsService(@Value("${aws.region}") String region) {
        this.secretsManagerClient = SecretsManagerClient.builder()
                .region(Region.of(region))
                .build();
    }

    public Map<String, Object> getSecret() {

        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = secretsManagerClient.getSecretValue(request);

        String secretString = response.secretString();

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(secretString, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing secret", e);
        }
    }
}