package com.hospital.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.model.Employee;
import com.hospital.service.impl.EmployeeServiceImpl;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class EmployeeController {
	@Autowired
	private EmployeeServiceImpl employeeService;
	
	@PostMapping
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
		employeeService.addEmployee(employee);
		HashMap<String, String> resp= new HashMap<String,String>();
		resp.put("message", "Employee added successfully");
		return ResponseEntity.ok(resp);
	}
	@GetMapping
	public List<Employee> getEmployee(){
		return employeeService.getEmployee();
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee,@PathVariable("id") int id){
		employeeService.updateEmployee(employee, id);
		HashMap<String, String> resp= new HashMap<String,String>();
		resp.put("message", "Employee updated successfully");
		return ResponseEntity.ok(resp);
	}
	@DeleteMapping()
	public ResponseEntity<?> deleteEmployee(@RequestBody Employee employee){
		employeeService.deleteEmployee(employee.getId());
		HashMap<String, String> resp= new HashMap<String,String>();
		resp.put("message", "Employee deleted successfully");
		return ResponseEntity.ok(resp);
	}
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable("id") int id) {
		return employeeService.getEmployeeById(id);
		
	}
	
}
