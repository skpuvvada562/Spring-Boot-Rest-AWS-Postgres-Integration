package com.spring.boot.rest.db.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class ClaimDocumentProcessor implements RequestHandler<S3Event, String> {

    private final SqsClient sqsClient;
    private final String queueUrl;

    public ClaimDocumentProcessor() {
        this.sqsClient = SqsClient.builder()
                .region(Region.AP_SOUTH_1)
                .build();
        // Read from Lambda Environment Variables, not Spring properties
        this.queueUrl = System.getenv("SQS_QUEUE_URL");
    }

    @Override
    public String handleRequest(S3Event event, Context context) {
        context.getLogger().log("Lambda triggered!");

        String bucket = event.getRecords().get(0).getS3().getBucket().getName();
        String key = event.getRecords().get(0).getS3().getObject().getKey();

        context.getLogger().log("Bucket: " + bucket + ", Key: " + key);

        // Guard against bad key format
        String[] parts = key.split("/");
        String claimId = parts.length > 1 ? parts[1] : key;

        String messageBody = "{ \"claimId\": \"" + claimId + "\", "
                + "\"bucket\": \"" + bucket + "\", "
                + "\"key\": \"" + key + "\" }";

        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build());

        context.getLogger().log("Message sent to SQS: " + messageBody);
        return "Message sent to SQS";
    }
}