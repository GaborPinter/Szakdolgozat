package com.example.AUTOKER3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AUTOKER3.beans.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>{
	
}
