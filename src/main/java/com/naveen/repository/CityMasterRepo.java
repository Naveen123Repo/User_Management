package com.naveen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.CityMasterEntity;

public interface CityMasterRepo extends JpaRepository<CityMasterEntity, Integer>{

	public List<CityMasterEntity> findByCityId(Integer cId);
}
