package com.oracle.www.sistemaempleadosappmonolitica.repository;

import com.oracle.www.sistemaempleadosappmonolitica.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
