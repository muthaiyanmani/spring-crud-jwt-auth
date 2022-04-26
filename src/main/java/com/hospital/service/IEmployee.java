package com.hospital.service;

import java.util.List;

import com.hospital.model.Employee;

public interface IEmployee {
	void addEmployee(Employee employee);
	List<Employee> getEmployee();
	void updateEmployee(Employee employee,int id);
	Employee getEmployeeById(int id);
	void deleteEmployee(int id);	
}
