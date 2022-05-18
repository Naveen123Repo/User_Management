package com.naveen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naveen.entity.UserDtlsEntity;

@Repository
public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer>{
	
	public UserDtlsEntity findByEmailAndPassword(String email, String pwd);
	
	
	public UserDtlsEntity findByEmail(String email);

}
