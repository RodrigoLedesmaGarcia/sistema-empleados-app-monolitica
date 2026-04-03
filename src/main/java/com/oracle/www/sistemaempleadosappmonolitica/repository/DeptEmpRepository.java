package com.oracle.www.sistemaempleadosappmonolitica.repository;

import com.oracle.www.sistemaempleadosappmonolitica.entities.DeptEmp;
import com.oracle.www.sistemaempleadosappmonolitica.entities.DeptEmpId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {

    @Modifying
    @Transactional
    @Query("delete from DeptEmp de where de.id.empNo = :empNo")
    void deleteByEmpNo(@Param("empNo") Integer empNo);

}
