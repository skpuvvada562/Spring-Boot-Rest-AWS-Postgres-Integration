package com.spring.boot.rest.db.dao;

import org.springframework.stereotype.Repository;

import com.spring.boot.rest.db.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

}
