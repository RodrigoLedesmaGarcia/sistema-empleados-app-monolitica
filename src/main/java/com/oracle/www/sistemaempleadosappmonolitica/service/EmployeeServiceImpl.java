package com.oracle.www.sistemaempleadosappmonolitica.service;

import com.oracle.www.sistemaempleadosappmonolitica.entities.DeptEmp;
import com.oracle.www.sistemaempleadosappmonolitica.entities.DeptEmpId;
import com.oracle.www.sistemaempleadosappmonolitica.entities.Employee;
import com.oracle.www.sistemaempleadosappmonolitica.entities.EmployeeCreateAndUpdateRequest;
import com.oracle.www.sistemaempleadosappmonolitica.repository.EmployeeMonolithRepository;
import com.oracle.www.sistemaempleadosappmonolitica.utils.EmployeeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMonolithRepository repository;

    public EmployeeServiceImpl(EmployeeMonolithRepository repository) {
        this.repository = repository;
    }

    private String quitarEspacios(String string){
        return (string == null) ? null : string.trim();
    }

    /*
    Buscar con filtros
     */
    @Override
    public Page<EmployeeProjection> findAllWithFilters(Integer empNo, LocalDate birthDate, String firstName, String lastName, String gender, LocalDate hireDate, String deptNo, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findAllWithFilters(
                empNo == null ? null :empNo.intValue(),
                birthDate,
                quitarEspacios(firstName),
                quitarEspacios(lastName),
                quitarEspacios(gender),
                hireDate,
                quitarEspacios(deptNo),
                fromDate,
                toDate,
                pageable
        );
    }

    /*
    crear empleado nuevo
     */
    @Override
    public void crearEmpleado(EmployeeCreateAndUpdateRequest request) {

        int nextEmpNo = repository.maxEmpNo() + 1;

        Employee employee = new Employee();
        employee.setEmpNo(nextEmpNo);
        employee.setBirthDate(request.getBirthDate());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setGender(request.getGender());
        employee.setHireDate(request.getHireDate());


        DeptEmp deptEmp = new DeptEmp();
        deptEmp.setId(new DeptEmpId(nextEmpNo, request.getDeptNo()));
        deptEmp.setFromDate(request.getFromDate());
        deptEmp.setToDate(request.getToDate());

        employee.getDepartaments().add(deptEmp);
        repository.save(employee);
    }


    /*
    editar empleado ya existente
     */
    @Override
    public void editarEmpleado(EmployeeCreateAndUpdateRequest request) {

        if (request.getEmpNo() == null){
            throw new RuntimeException("El N° es obligatorio para editar");
        }

        Employee employee = repository.findById(request.getEmpNo())
                .orElseThrow( () -> new RuntimeException(
                        "Empleado no encontrado: " + request.getEmpNo())
        );

        employee.setBirthDate(request.getBirthDate());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setGender(request.getGender());
        employee.setHireDate(request.getHireDate());

        if(employee.getDepartaments() != null){
            employee.getDepartaments().clear();
        }

        DeptEmp deptEmp = new DeptEmp();
        deptEmp.setId(new DeptEmpId(employee.getEmpNo(), request.getDeptNo()));
        deptEmp.setFromDate(request.getFromDate());
        deptEmp.setToDate(request.getToDate());

        employee.getDepartaments().add(deptEmp);

        repository.save(employee);
    }


    /*
    eliminar empleado
     */
    @Override
    public void eliminarEmpleado(Integer empNo) {
        Employee employee = repository.findById(empNo)
                .orElseThrow( () -> new RuntimeException(
                        "Empleado no encontrado: " + empNo)
        );

        if(employee.getDepartaments() != null){
            employee.getDepartaments().clear();
        }

        repository.delete(employee);

    }
}
