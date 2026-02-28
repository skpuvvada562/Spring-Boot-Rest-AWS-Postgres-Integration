package com.spring.boot.rest.db.pojo;

public record ClaimDocumentEventDto(int id, String claimId, String bucket, String key,String status) {

}
