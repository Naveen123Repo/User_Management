package com.naveen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.StateMasterEntity;

public interface StateMasterRepo extends JpaRepository<StateMasterEntity, Integer>{

	
	public List<StateMasterEntity> findByCountryId(Integer cId);
}
