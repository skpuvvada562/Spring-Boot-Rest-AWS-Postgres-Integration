package com.spring.boot.rest.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.rest.db.dao.ClaimDao;
import com.spring.boot.rest.db.entity.ClaimDocumentEvent;

@Service
public class ClaimService {

	@Autowired
	private ClaimDao claimDao;
	
	public ClaimDocumentEvent updateStatus(String claimId, String status) {
		
		ClaimDocumentEvent event=new ClaimDocumentEvent();
		event.setClaimId(claimId);
		event.setStatus(status);
		
		return claimDao.save(event);
		
	}
}
