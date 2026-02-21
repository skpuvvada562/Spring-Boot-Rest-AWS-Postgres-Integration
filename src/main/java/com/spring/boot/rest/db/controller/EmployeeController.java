package com.spring.boot.rest.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.rest.db.pojo.EmployeeDto;
import com.spring.boot.rest.db.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService empService;

	@PostMapping("/save")
	public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto empDto) {
		
		EmployeeDto dto=empService.saveEmployeeService(empDto);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {

		List<EmployeeDto> empList = empService.getAllEmployeesService();
		
		return ResponseEntity.ok(empList);
	}
	
	@GetMapping("/getId/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable int id) {

		EmployeeDto emp = empService.getEmployeeByIdService(id);

		return ResponseEntity.ok(emp);
	}
}
