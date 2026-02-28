package com.spring.boot.rest.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClaimDocumentEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	 private String claimId;
	    private String status;
		public String getClaimId() {
			return claimId;
		}
		public void setClaimId(String claimId) {
			this.claimId = claimId;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		@Override
		public String toString() {
			return "ClaimDocumentEvent [id=" + id + ", claimId=" + claimId + ", status=" + status + "]";
		}
	
		
	    
}
