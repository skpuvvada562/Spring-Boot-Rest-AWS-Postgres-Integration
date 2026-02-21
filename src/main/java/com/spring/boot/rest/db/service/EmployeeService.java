package com.spring.boot.rest.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.rest.db.config.AppConfig;
import com.spring.boot.rest.db.dao.EmployeeDao;
import com.spring.boot.rest.db.entity.Employee;
import com.spring.boot.rest.db.pojo.EmployeeDto;

@Service
public class EmployeeService {

	@Autowired
	EmployeeDao empDao;
	
	@Autowired
	AppConfig appConfig;
	
	public EmployeeDto saveEmployeeService(EmployeeDto empDto) {
		
		Employee emp = appConfig.modelMapper().map(empDto, Employee.class);
		EmployeeDto dto = appConfig.modelMapper().map(emp, EmployeeDto.class);
		
		emp=empDao.save(emp);
		return dto;
	}

	public List<EmployeeDto> getAllEmployeesService() {
		return empDao.findAll().stream().map(emp->appConfig.modelMapper().map(emp, EmployeeDto.class)).toList();
	}

	public EmployeeDto getEmployeeByIdService(int id) {
		// TODO Auto-generated method stub
		return empDao.findById(id).map(emp->appConfig.modelMapper().map(emp, EmployeeDto.class)).orElse(null);
	}

}
