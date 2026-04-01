package com.oracle.www.sistemaempleadosappmonolitica.security.repository;

import com.oracle.www.sistemaempleadosappmonolitica.entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    UserLogin findByUsername(String username);
}
