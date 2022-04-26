package com.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
