package com.oracle.www.sistemaempleadosappmonolitica.utils;

import java.time.LocalDate;

public interface EmployeeProjection {

    Integer getEmpNo();

    LocalDate getBirthDate();

    String getFirstName();

    String getLastName();

    String getGender();

    LocalDate getHireDate();

    String getDeptNo();

    LocalDate getFromDate();

    LocalDate getToDate();
}
