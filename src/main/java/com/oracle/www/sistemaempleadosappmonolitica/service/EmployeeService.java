package com.oracle.www.sistemaempleadosappmonolitica.service;

import com.oracle.www.sistemaempleadosappmonolitica.entities.EmployeeCreateAndUpdateRequest;
import com.oracle.www.sistemaempleadosappmonolitica.utils.EmployeeProjection;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface EmployeeService {

    Page<EmployeeProjection> findAllWithFilters(
            Integer empNo,
            LocalDate birthDate,
            String firstName,
            String lastName,
            String gender,
            LocalDate hireDate,
            String deptNo,
            LocalDate formDate,
            LocalDate toDate,
            int page,
            int size
    );

    void crearEmpleado(EmployeeCreateAndUpdateRequest request);

    void editarEmpleado(EmployeeCreateAndUpdateRequest request);

    void eliminarEmpleado(Integer empNo);
}
