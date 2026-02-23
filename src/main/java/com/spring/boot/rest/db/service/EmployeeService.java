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
		Employee emp=new Employee();
		emp=convertDtoToEntity(empDto,emp);
		emp=empDao.save(emp);
		empDto =convertEntityToDto(emp,empDto);
		return empDto;
	}

	private EmployeeDto convertEntityToDto(Employee emp, EmployeeDto empDto) {
		return empDto = new EmployeeDto(emp.getId(),emp.getName(), emp.getEmail(), emp.getAge(), emp.getSalary());
	}

	private Employee convertDtoToEntity(EmployeeDto empDto, Employee emp) {
		emp.setAge(empDto.age());
		emp.setEmail(empDto.email());
		emp.setName(empDto.name());
		emp.setSalary(empDto.salary());
		return emp;
	}

	public List<EmployeeDto> getAllEmployeesService() {
		return empDao.findAll()
					 .stream()
					 .map(emp->new EmployeeDto(emp.getId(),emp.getName(), emp.getEmail(), emp.getAge(), emp.getSalary()))
					 .toList();
	}

	public EmployeeDto getEmployeeByIdService(int id) {
		// TODO Auto-generated method stub
		return empDao.findById(id)
					 .map(emp->new EmployeeDto(emp.getId(),emp.getName(), emp.getEmail(), emp.getAge(), emp.getSalary()))
					 .orElse(null);
	}

}
