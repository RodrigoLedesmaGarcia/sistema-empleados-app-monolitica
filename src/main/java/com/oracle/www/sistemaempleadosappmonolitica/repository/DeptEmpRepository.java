package com.oracle.www.sistemaempleadosappmonolitica.repository;

import com.oracle.www.sistemaempleadosappmonolitica.entities.DeptEmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptEmpRepository extends JpaRepository<DeptEmp, Integer> {
}
