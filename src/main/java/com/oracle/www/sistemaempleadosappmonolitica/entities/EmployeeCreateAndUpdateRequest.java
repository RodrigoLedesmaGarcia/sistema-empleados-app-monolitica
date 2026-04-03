package com.oracle.www.sistemaempleadosappmonolitica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Table(name = "employees")
public class EmployeeCreateAndUpdateRequest {

    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @NotNull(message = "el campo de no puede estar vacio")
    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @NotBlank(message = "el campo de nombre no puede estar vacio")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "el campo de apellido no puede estar vacio")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "el campo de genero no puede estar vacio")
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull(message = "el campo de fecha de contraracion no puede estar vacio")
    @Column(name = "hire_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate hireDate;

    @NotNull(message = "el campo de deparyamento no puede estar vacio")
    @Column(name = "dept_no", nullable = false)
    private String deptNo;

    @NotNull(message = "el campo de inicio no puede estar vacio")
    @Column(name = "from_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;

    public EmployeeCreateAndUpdateRequest() {
    }


    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
