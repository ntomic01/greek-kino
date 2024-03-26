package com.web.greekkino.repository;


import com.web.greekkino.domain.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SystemUser, Long> {

    SystemUser findByUsername(String username);

}
