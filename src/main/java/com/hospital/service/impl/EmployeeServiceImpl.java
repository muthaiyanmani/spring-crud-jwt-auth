package com.hospital.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.exception.ResourceNotFoundException;
import com.hospital.model.Employee;
import com.hospital.repo.EmployeeRepository;
import com.hospital.service.IEmployee;


@Service
public class EmployeeServiceImpl implements IEmployee {
	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public void addEmployee(Employee employee) {
		employeeRepo.save(employee);
	}
	@Override
	public List<Employee> getEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public void updateEmployee(Employee employee,int id) {
		Employee exisitingUser = employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee", "id", id));
		exisitingUser.setName(employee.getName());
		exisitingUser.setEmail(employee.getEmail());
		exisitingUser.setMobile(employee.getMobile());
		exisitingUser.setSalary(employee.getSalary());
		employeeRepo.save(exisitingUser);
	}

	@Override
	public Employee getEmployeeById(int id) {
		return employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee", "id", id));
	}

	@Override
	public void deleteEmployee(int id) {
		employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee", "id", id));
		employeeRepo.deleteById(id);
		
	}


}
