package com.citiustech.pms.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citiustech.pms.auth.model.LoginDao;

@Repository
public interface LoginRepository extends JpaRepository<LoginDao, Integer> {
	
	LoginDao findByUsername(String username);

}
