package com.spring.boot.rest.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.rest.db.entity.ClaimDocumentEvent;

@Repository
public interface ClaimDao  extends JpaRepository<ClaimDocumentEvent, Integer>{

}
