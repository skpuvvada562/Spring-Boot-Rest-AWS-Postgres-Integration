package com.spring.boot.rest.db.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.spring.boot.rest.db.entity.ClaimDocumentEvent;
import com.spring.boot.rest.db.service.ClaimService;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class ClaimDocumentListener {

	@Autowired
	private ClaimService claimService;
	
    @SqsListener("claim-document-queue")
    public void receiveMessage(@Payload String message) {

        System.out.println("Received: " + message);

        // Parse JSON (use ObjectMapper in real project)

        String claimId = extractClaimId(message);

        // Update DB
        ClaimDocumentEvent event= claimService.updateStatus(claimId, "DOCUMENT_UPLOADED");
        System.out.println("document uploaded succesfully:::"+event);
    }

    private String extractClaimId(String message) {
        return message.split("\"claimId\": \"")[1].split("\"")[0];
    }
}